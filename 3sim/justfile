# Load environment variables from .env file

set dotenv-load := true

# We need to disable tycho javadoc, because it is not compatible with the current JDK.
# We also disable xtend compilation, because xtend is not used in 3SIM.

MVN_ARGS := "!tycho-javadoc-for-bundle,!tycho-javadoc-for-updatesite,!compile-xtend"

check-local-build-cache-exists:
    #!/usr/bin/env sh
    if [ ! -d ".build-cache/local-cache-repo" ]; then
      echo "Local build cache does not exist. Please run 'just build' first."
      exit 1
    fi

clean-local-build-cache:
    rm -rf .build-cache/local-cache-repo/

populate-local-build-cache:
    mkdir -p .build-cache/local-cache-repo/
    cp -a releng/org.palladiosimulator.blockchainsystems.updatesite/target/repository/. .build-cache/local-cache-repo/

clean:
    mvn clean -P '{{ MVN_ARGS }}'
    just clean-local-build-cache

verify:
    mvn verify -P '{{ MVN_ARGS }}'

build:
    just clean-local-build-cache
    mvn install -P '{{ MVN_ARGS }}'
    just populate-local-build-cache

build-feature feature: check-local-build-cache-exists
    mvn install -P '{{ MVN_ARGS }},!build-all,build-{{ feature }}-feature'
    just clean-local-build-cache # Ensure the old local cache is only cleaned after build finished.
    just populate-local-build-cache

# Run Eclipse with remote debugging enabled.
debug:
    $ECLIPSE_EXECUTABLE -vmargs -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:5005

serve-updatesite:
    sfz -a -b 100.123.232.93 -p 5000 ./releng/org.palladiosimulator.blockchainsystems.updatesite/target/repository/

