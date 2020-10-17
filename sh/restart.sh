#!/bin/sh
kill -9 `cat meeting.pid`
echo "sh日志->进程被结束……"
rm -rf meeting.log*
echo "sh日志->日志文件被移除……"
mvn clean package -Dmaven.test.skip=true
echo "sh日志->重新打包完成……"
nohup java -jar ./target/meeting.jar >meeting.log 2>&1 & echo $! > meeting.pid
tailf meeting.log
