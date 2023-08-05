# #!/bin/bash
# REPOSITORY=/home/ubuntu/app
# BUILD_PATH=$(ls /home/ubuntu/app/*SNAPSHOT.jar)
# JAR_NAME=$(basename $BUILD_PATH)
# echo "> build 파일명: $JAR_NAME"

# echo "> application.jar 교체"
# IDLE_APPLICATION=api-0.0.1-SNAPSHOT.jar
# IDLE_APPLICATION_PATH=$REPOSITORY/$IDLE_APPLICATION

# echo "> $IDLE_APPLICATION 애플리케이션 pid 확인"
# IDLE_PID=$(pgrep -f $IDLE_APPLICATION)

# if [ -z "$IDLE_PID" ]; then
#   echo "현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
# else
#   echo "> kill -15 $IDLE_PID"
#   kill -15 $IDLE_PID
#   sleep 5
# fi

# echo "> $JAR_NAME 실행"
# nohup java -jar -Duser.timezone=Asia/Seoul -Dspring.profiles.active=dev $IDLE_APPLICATION_PATH >> /home/ubuntu/app/nohup.out 2>&1 &


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

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi

nohup java -jar -Duser.timezone=Asia/Seoul -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=dev $IDLE_APPLICATION_PATH >> /home/ubuntu/app/nohup.out 2>&1 &


