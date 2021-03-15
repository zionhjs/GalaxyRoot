#!/bin/bash
#Service_name=galaxy-upload
#Docker_running=`sudo docker ps |grep $Service_name`
#
#if [ -n "$Doocker_running" ];then
#     sudo docker rm -f $Service_name >/dev/null 2>1
#     sudo docker rmi -f `sudo docker images|grep -v openjdk|awk '{print $3}'|grep -v IMAGE` >/dev/null 2>1
#else
#     exit 0
#fi
