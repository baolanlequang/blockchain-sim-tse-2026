TRILEMMA standalone builder — Windows source-path revision (2026-08-24C)

This revision fixes:
  * PowerShell NativeCommandError around java -version
  * missing cmd.exe/System32 dependency
  * javac "Invalid filename" caused by Windows backslashes/BOM in @argument files

The builder now:
  * resolves java/javac/jar/javap directly from JAVA_HOME;
  * passes each Java source path directly to javac as a PowerShell argument;
  * does not create or use a javac @source-file argument file.

Prerequisites:
  1. JAVA_HOME points to JDK 21.
  2. The 3SIM Maven/Tycho build already ended with BUILD SUCCESS.

From repository root:
  Set-ExecutionPolicy -Scope Process Bypass
  .\TRILEMMA_STANDALONE_BUILD\build_trilemma_standalone.ps1
