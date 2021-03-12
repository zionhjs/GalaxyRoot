#!/bin/bash

sudo docker rm -f $PROJECT






# old version
##!/usr/bin/env bash
#
#stop_process_by_service_name() {
#    service_name=$1
#    if [[ x${service_name} == "x" ]]; then
#        echo "service name is empty, exit..."
#        exit 1
#    fi
#
#
#    pid=$(ps -ef | grep "${service_name}" | grep -v grep | awk '{print $2}')
#    if [[ -z ${pid} ]]; then
#        echo "service ${service_name} is not running..."
#    else
#        until [[ -z ${pid} ]]; do
#            echo "service ${service_name} will be stopped..."
#            kill "${pid}"
#            sleep 5
#            pid=$(ps -ef | grep "${service_name}" | grep -v grep | awk '{print $2}')
#        done
#
#        echo "service ${service_name} has been stopped..."
#        sleep 5
#    fi
#}
#
#stop_process_by_service_name "galaxy-upload"
#stop_process_by_service_name "galaxy-ucenter"
#stop_process_by_service_name "galaxy-cms"
#stop_process_by_service_name "galaxy-gateway"
#stop_process_by_service_name "galaxy-config"
#stop_process_by_service_name "galaxy-eureka"
