@rem Gradle wrapper startup script for Windows.
@echo off
setlocal

set APP_HOME=%~dp0
set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

if defined JAVA_HOME (
	set JAVACMD=%JAVA_HOME%\bin\java.exe
) else (
	set JAVACMD=java.exe
)

if not exist "%CLASSPATH%" (
	echo ERROR: Gradle wrapper jar not found at %CLASSPATH%
	echo Download gradle-wrapper.jar for Gradle 8.14.3 into gradle\wrapper before running this script.
	exit /b 1
)

"%JAVACMD%" -Xmx64m -Xms64m -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
endlocal
