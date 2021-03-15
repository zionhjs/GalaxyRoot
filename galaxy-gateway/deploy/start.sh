#!/bin/bash
sleep 480

sudo docker run -p 9400:9400 -d --name galaxy-gateway 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-gateway:latest
