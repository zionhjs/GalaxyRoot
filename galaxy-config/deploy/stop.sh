#!/bin/bash
Service_name=galaxy-config
Docker_running=`sudo docker ps |sudo grep $Service_name`

if [ -n "$Doocker_running" ];then
     sudo docker rm -f $Service_name >/dev/null 2>1
     sudo docker rmi -f `sudo docker images|sudo grep -v openjdk|sudo awk '{print $3}'|sudo grep -v IMAGE` >/dev/null 2>1
else
     sudo echo 'exit 0'
fi