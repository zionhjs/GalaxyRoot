#!/bin/bash

sleep 480

sudo docker run -p 9900:9900 -d --name galaxy-cms 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-cms:latest
