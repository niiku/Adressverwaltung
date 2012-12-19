@echo off
set DERBY_HOME=C:\Programme2\db-derby-10.5.3.0-bin
java -jar %DERBY_HOME%\lib\derbyrun.jar ij < derby.sql
pause
