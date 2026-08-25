param(
    [string]$RepoRoot = "",
    [string]$OutputJar = ""
)

$ErrorActionPreference = "Stop"
Set-StrictMode -Version Latest

function Fail([string]$Message) {
    Write-Host "ERROR: $Message" -ForegroundColor Red
    exit 1
}

function Run([string]$Exe, [string[]]$Args) {
    & $Exe @Args
    if ($LASTEXITCODE -ne 0) {
        Fail "$Exe failed with exit code $LASTEXITCODE"
    }
}

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
if ([string]::IsNullOrWhiteSpace($RepoRoot)) {
    # Recommended placement: <repo>\TRILEMMA_STANDALONE_BUILD\this-script.ps1
    $RepoRoot = Split-Path -Parent $scriptDir
}
$RepoRoot = (Resolve-Path $RepoRoot).Path

$threeSimRoot = Join-Path $RepoRoot "3sim"
$trilemmaProject = Join-Path $RepoRoot "trilemma\org.palladiosimulator.blockchainsystems.trilemma"
$dependencyJar = Join-Path $scriptDir "trilemma-runtime-dependencies.jar"

if (-not (Test-Path $threeSimRoot)) { Fail "3SIM root not found: $threeSimRoot" }
if (-not (Test-Path $trilemmaProject)) { Fail "Trilemma project not found: $trilemmaProject" }
if (-not (Test-Path $dependencyJar)) { Fail "Dependency reservoir not found: $dependencyJar" }

Write-Host "Repository: $RepoRoot"
Write-Host "Trilemma:  $trilemmaProject"
Write-Host ""

# -----------------------------------------------------------------------------
# 1. Toolchain check: this source tree targets Java 21.
#
# Use JAVA_HOME directly rather than relying on PATH or cmd.exe. This makes the
# builder robust on Windows installations where System32/cmd.exe is not visible
# on PATH, and it also guarantees that javac/jar/javap come from the same JDK.
# -----------------------------------------------------------------------------
if ([string]::IsNullOrWhiteSpace($env:JAVA_HOME)) {
    Fail "JAVA_HOME is not set. Point it to JDK 21, e.g. C:\Program Files\Java\jdk-21.0.12"
}

$javaHome = $env:JAVA_HOME.TrimEnd('\')
$javaExe  = Join-Path $javaHome "bin\java.exe"
$javacExe = Join-Path $javaHome "bin\javac.exe"
$jarExe   = Join-Path $javaHome "bin\jar.exe"
$javapExe = Join-Path $javaHome "bin\javap.exe"

foreach ($tool in @($javaExe, $javacExe, $jarExe, $javapExe)) {
    if (-not (Test-Path $tool)) {
        Fail "Required JDK tool not found: $tool"
    }
}

$javacVersion = ((& $javacExe -version) | Out-String).Trim()
if ($LASTEXITCODE -ne 0) {
    Fail "Could not execute javac from JAVA_HOME: $javacExe"
}

Write-Host "JAVA_HOME: $javaHome"
Write-Host $javacVersion

if ($javacVersion -notmatch '21(?:\.|\s|$)') {
    Fail "JDK 21 is required. JAVA_HOME currently resolves to: $javaHome"
}

# -----------------------------------------------------------------------------
# 2. Use ONLY freshly Maven-built 3SIM jars. This prevents stale implementation
#    classes from leaking into the runnable pilot jar.
# -----------------------------------------------------------------------------
$modules = @(
    "org.palladiosimulator.blockchainsystems.bscm",
    "org.palladiosimulator.blockchainsystems.core",
    "org.palladiosimulator.blockchainsystems.loggers",
    "org.palladiosimulator.blockchainsystems.plugin",
    "org.palladiosimulator.blockchainsystems.kotlin-deps",
    "org.palladiosimulator.blockchainsystems.threesim"
)

$moduleJars = New-Object System.Collections.Generic.List[string]
foreach ($module in $modules) {
    $moduleDir = Join-Path $threeSimRoot $module
    if (-not (Test-Path $moduleDir)) { Fail "Required 3SIM module missing: $moduleDir" }

    $jar = Get-ChildItem (Join-Path $moduleDir "target") -Filter "*.jar" -File -ErrorAction SilentlyContinue |
        Where-Object { $_.Name -notmatch '(sources|javadoc)' } |
        Sort-Object LastWriteTime -Descending |
        Select-Object -First 1
    if ($null -eq $jar) {
        Fail "No built jar for $module. Run 'mvn -U clean verify' from the 3sim directory first."
    }

    $srcDir = Join-Path $moduleDir "src"
    if (Test-Path $srcDir) {
        $latestSource = Get-ChildItem $srcDir -Recurse -File -Include *.kt,*.java -ErrorAction SilentlyContinue |
            Sort-Object LastWriteTime -Descending |
            Select-Object -First 1
        if ($null -ne $latestSource -and $jar.LastWriteTime -lt $latestSource.LastWriteTime) {
            Fail "Built jar is older than source for $module. Re-run the clean 3SIM Maven build before continuing.`nJar: $($jar.LastWriteTime)`nSource: $($latestSource.LastWriteTime) $($latestSource.FullName)"
        }
    }

    Write-Host ("3SIM jar OK: {0}  [{1}]" -f $jar.Name, $jar.LastWriteTime)
    $moduleJars.Add($jar.FullName)
}

# -----------------------------------------------------------------------------
# 3. Compile Trilemma with javac directly. This intentionally bypasses Eclipse
#    PDE's broken/foreign target-platform metadata, which was generating ECJ
#    class files containing 'Unresolved compilation problem' stubs.
# -----------------------------------------------------------------------------
$externalLibDir = Join-Path $trilemmaProject "external-libs"
$externalLibs = @()
if (Test-Path $externalLibDir) {
    $externalLibs = @(Get-ChildItem $externalLibDir -Filter "*.jar" -File | ForEach-Object { $_.FullName })
}

$buildDir = Join-Path $trilemmaProject "standalone-build"
$classesDir = Join-Path $buildDir "classes"
$stageDir = Join-Path $buildDir "fatjar-stage"
$manifestFile = Join-Path $buildDir "MANIFEST.MF"

if (Test-Path $buildDir) { Remove-Item $buildDir -Recurse -Force }
New-Item $classesDir -ItemType Directory -Force | Out-Null
New-Item $stageDir -ItemType Directory -Force | Out-Null

$sources = @(Get-ChildItem (Join-Path $trilemmaProject "src") -Recurse -Filter "*.java" -File)
if ($sources.Count -eq 0) { Fail "No Trilemma Java sources found." }

$cpEntries = @($dependencyJar) + @($moduleJars) + @($externalLibs)
$classPath = [string]::Join([IO.Path]::PathSeparator, $cpEntries)

# IMPORTANT:
# Do not use a javac @argument-file for Windows source paths here. javac's
# argument-file parser treats backslashes as escape characters, so paths such
# as "\trilemma\..." can be corrupted (for example \t becomes a tab). It can
# also interpret a UTF-8 BOM as part of the first filename. There are only a
# handful of Trilemma source files, so pass each source path directly as a
# PowerShell argument instead.
$sourcePaths = @($sources | ForEach-Object { $_.FullName })

$javacArgs = @(
    "--release", "21",
    "-encoding", "UTF-8",
    "-cp", $classPath,
    "-d", $classesDir
) + $sourcePaths

Write-Host ""
Write-Host "Compiling $($sources.Count) Trilemma Java source files with javac..."
& $javacExe @javacArgs
if ($LASTEXITCODE -ne 0) {
    Fail "Trilemma compilation failed. Fix the FIRST javac error shown above; do not export/run a jar containing Eclipse error stubs."
}
Write-Host "Trilemma javac compilation: PASS" -ForegroundColor Green

# -----------------------------------------------------------------------------
# 4. Assemble a fresh fat jar from:
#      external dependency reservoir (NO old blockchain-simulator classes),
#      current Maven-built 3SIM jars,
#      current explicit runtime libraries,
#      freshly compiled Trilemma classes.
# -----------------------------------------------------------------------------
function Expand-Jar([string]$JarPath, [string]$Destination) {
    Push-Location $Destination
    try {
        & $jarExe xf $JarPath
        if ($LASTEXITCODE -ne 0) { Fail "Could not unpack jar: $JarPath" }
    } finally {
        Pop-Location
    }
}

Expand-Jar $dependencyJar $stageDir
foreach ($j in $moduleJars) { Expand-Jar $j $stageDir }
foreach ($j in $externalLibs) { Expand-Jar $j $stageDir }
Copy-Item (Join-Path $classesDir "*") $stageDir -Recurse -Force

# Remove signatures/manifests inherited from source jars.
$metaInf = Join-Path $stageDir "META-INF"
if (Test-Path $metaInf) {
    Remove-Item (Join-Path $metaInf "MANIFEST.MF") -Force -ErrorAction SilentlyContinue
    Get-ChildItem $metaInf -File -ErrorAction SilentlyContinue |
        Where-Object { $_.Extension -in @('.SF','.RSA','.DSA') -or $_.Name -eq 'INDEX.LIST' } |
        Remove-Item -Force
}

@"
Manifest-Version: 1.0
Main-Class: org.palladiosimulator.blockchainsystems.trilemma.TrilemmaSimulator
Implementation-Title: Refined 3SIM Trilemma Runner
Implementation-Version: 2026-08-25
"@ | Set-Content $manifestFile -Encoding ASCII

if ([string]::IsNullOrWhiteSpace($OutputJar)) {
    $OutputJar = Join-Path (Split-Path -Parent $trilemmaProject) "trilemma.jar"
}
$OutputJar = [IO.Path]::GetFullPath($OutputJar)
if (Test-Path $OutputJar) { Remove-Item $OutputJar -Force }

Push-Location $stageDir
try {
    & $jarExe cfm $OutputJar $manifestFile .
    if ($LASTEXITCODE -ne 0) { Fail "jar assembly failed." }
} finally {
    Pop-Location
}

# -----------------------------------------------------------------------------
# 5. Preflight verification: make sure the exact classes from the user's error
#    are actually inside the new jar and that ECJ error stubs are absent.
# -----------------------------------------------------------------------------
$jarList = & $jarExe tf $OutputJar
$requiredClasses = @(
    "org/apache/log4j/Logger.class",
    "org/eclipse/emf/ecore/plugin/EcorePlugin.class",
    "tools/mdsd/library/standalone/initialization/StandaloneInitializerBuilder.class",
    "org/palladiosimulator/blockchainsystems/core/simulation/abstractions/SimulationParameters.class",
    "org/palladiosimulator/blockchainsystems/threesim/simulation/results/ThreesimSimulationResultSerializer.class",
    "com/google/gson/Gson.class",
    "org/glassfish/hk2/osgiresourcelocator/Activator.class",
    "org/palladiosimulator/blockchainsystems/trilemma/TrilemmaSimulator.class",
    "org/palladiosimulator/blockchainsystems/trilemma/BlockchainTrilemmaStandalone.class"
)
foreach ($required in $requiredClasses) {
    if ($jarList -notcontains $required) { Fail "Final jar is missing required class: $required" }
}

$javap1 = (& $javapExe -classpath $OutputJar -c org.palladiosimulator.blockchainsystems.trilemma.BlockchainTrilemmaStandalone 2>&1 | Out-String)
$javap2 = (& $javapExe -classpath $OutputJar -c org.palladiosimulator.blockchainsystems.trilemma.TrilemmaSimulator 2>&1 | Out-String)
if (($javap1 + $javap2) -match "Unresolved compilation") {
    Fail "The final jar still contains an Eclipse unresolved-compilation stub."
}

Write-Host ""
Write-Host "============================================================" -ForegroundColor Green
Write-Host "STANDALONE TRILEMMA BUILD SUCCESS" -ForegroundColor Green
Write-Host "Jar: $OutputJar"
Write-Host "Size: $([Math]::Round((Get-Item $OutputJar).Length / 1MB, 1)) MB"
Write-Host "============================================================" -ForegroundColor Green
Write-Host ""
Write-Host "Next: run the packaged regression smoke manifest first. Example:"
Write-Host "  cd `"$trilemmaProject`""
Write-Host "  java -jar `"$OutputJar`" .\regression_smoke_final.csv .\testmodels .\testmodels\configuration_refined_final_smoke.json"
