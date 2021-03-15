#!/bin/bash

sleep 270

sudo docker run -p 9600:9600 -d --name galaxy-ucenter 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-ucenter:latest

sudo !94