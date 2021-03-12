#!/bin/bash

sudo docker run --name $PROJECT $REPOSITORY_URI:latest




# old version
#!/usr/bin/env bas

#workspace=/home/ubuntu/galaxy-root
#cd ${workspace}
#
#start_process_by_process_name() {
#    service_name=$1
#    if [[ x${service_name} == "x" ]]; then
#        echo "service name is empty, exit..."
#        exit 1
#    fi
#
#    pid=$(ps -ef | grep "${service_name}" | grep -v grep | awk '{print $2}')
#    if [[ x${pid} != "x" ]]; then
#        echo "service ${service_name} is running, stop it first..."
#    else
#        nohup java -jar ${service_name}-1.0.0.jar >> ${workspace}/galaxy-root.log 2>&1 &
#        echo "service ${service_name} is started..."
#        sleep 5
#    fi
#}
#
#start_process_by_process_name "galaxy-eureka"
#start_process_by_process_name "galaxy-config"
#start_process_by_process_name "galaxy-gateway"
#start_process_by_process_name "galaxy-cms"
#start_process_by_process_name "galaxy-ucenter"
#start_process_by_process_name "galaxy-upload"
#start_process_by_process_name "galaxy-admin-server"
