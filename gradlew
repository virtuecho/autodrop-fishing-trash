#!/bin/sh

APP_BASE_NAME=${0##*/}
APP_HOME=$(cd "${0%/*}" >/dev/null 2>&1 && pwd -P) || exit

DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

if [ -n "$JAVA_HOME" ]; then
	JAVACMD=$JAVA_HOME/bin/java
else
	JAVACMD=java
fi

if [ -n "$JAVA_HOME" ] && [ ! -x "$JAVACMD" ]; then
	echo "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME" >&2
	exit 1
fi

if ! command -v "$JAVACMD" >/dev/null 2>&1; then
	echo "ERROR: Java could not be found. Please install JDK 21 or set JAVA_HOME." >&2
	exit 1
fi

if [ ! -f "$CLASSPATH" ]; then
	echo "ERROR: Gradle wrapper jar not found at $CLASSPATH" >&2
	echo "Run: curl -L -o gradle/wrapper/gradle-wrapper.jar https://github.com/gradle/gradle/raw/v8.14.3/gradle/wrapper/gradle-wrapper.jar" >&2
	exit 1
fi

eval "set -- $DEFAULT_JVM_OPTS -classpath \"\$CLASSPATH\" org.gradle.wrapper.GradleWrapperMain \"\$@\""
exec "$JAVACMD" "$@"
