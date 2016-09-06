@ECHO OFF
set SAVE_JAVA_HOME=%JAVA_HOME%

set SAVE_PATH=%path%
set JAVA_HOME=java
set PATH=;%JAVA_HOME%\bin;%PATH%

cd Application
start ..\%JAVA_HOME%\bin\javaw -jar "ecasta-1.0-jar-with-dependencies.jar"

set JAVA_HOME=%SAVE_JAVA_HOME%
set PATH=%SAVE_PATH%
exit