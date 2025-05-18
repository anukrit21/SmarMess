@echo off
set KAFKA_VERSION=3.6.1
set KAFKA_DIR=kafka_2.13-%KAFKA_VERSION%

if not exist %KAFKA_DIR% (
    echo Downloading Kafka...
    powershell -Command "Invoke-WebRequest -Uri 'https://downloads.apache.org/kafka/%KAFKA_VERSION%/kafka_2.13-%KAFKA_VERSION%.tgz' -OutFile 'kafka.tgz'"
    tar -xf kafka.tgz
    del kafka.tgz
)

cd %KAFKA_DIR%

echo Starting Zookeeper...
start /B bin\windows\zookeeper-server-start.bat config\zookeeper.properties

timeout /t 10

echo Starting Kafka...
start /B bin\windows\kafka-server-start.bat config\server.properties

echo Kafka is starting... 