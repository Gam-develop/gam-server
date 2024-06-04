#!/bin/bash
REPOSITORY=/home/ubuntu/app
BUILD_PATH=$(ls /home/ubuntu/app/*SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_PATH)
IDLE_APPLICATION_PATH=/home/ubuntu/app/${JAR_NAME}
TARGET_PORT=8080
CURRENT_PORT=8080

CURRENT_PID=$(lsof -Fp -i TCP:${CURRENT_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')
sudo kill ${CURRENT_PID}

if [ "$DEPLOYMENT_GROUP_NAME" == "prod" ]
then
  nohup java -jar -Duser.timezone=Asia/Seoul -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=prod $IDLE_APPLICATION_PATH >> /home/ubuntu/app/nohup.out 2>&1 &
fi

if [ "$DEPLOYMENT_GROUP_NAME" == "dev" ]
then
   nohup java -jar -Duser.timezone=Asia/Seoul -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=dev $IDLE_APPLICATION_PATH >> /home/ubuntu/app/nohup.out 2>&1 &
fi
