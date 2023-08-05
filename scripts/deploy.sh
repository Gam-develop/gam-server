#!/bin/bash
REPOSITORY=/home/ubuntu/app
BUILD_PATH=$(ls /home/ubuntu/app/*SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_PATH)
echo "> build 파일명: $JAR_NAME"

CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current port of running WAS is ${CURRENT_PORT}."

if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "> No WAS is connected to nginx"
fi
echo "> Target port is  ${TARGET_PORT}."

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

# if [ ! -z ${TARGET_PID} ]; then
#   echo "> Kill WAS running at ${TARGET_PORT}."
#   sudo kill ${TARGET_PID}
# fi

nohup java -jar -Duser.timezone=Asia/Seoul -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=dev $IDLE_APPLICATION_PATH >> /home/ubuntu/app/nohup.out 2>&1 &


echo "> Now new WAS runs at ${TARGET_PORT}."
