
set PROJ_DIR=%CD%
set DEPLOY_TEMP_DIR=C:\Softwares\sgescolar

CALL mvnw clean package -DskipTests

@echo on

del /s /y %DEPLOY_TEMP_DIR%\src\*
del /s /y %DEPLOY_TEMP_DIR%\target\*

xcopy /s /y src\* %DEPLOY_TEMP_DIR%\src\
xcopy /s /y target\* %DEPLOY_TEMP_DIR%\target\

copy %DEPLOY_TEMP_DIR%\application.yml %DEPLOY_TEMP_DIR%\src\main\resources\application.yml

cd %DEPLOY_TEMP_DIR%

call git add .
call git commit -m %1
call git push heroku main

cd %PROJ_DIR%