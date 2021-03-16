#!/bin/bash

sleep 210

sudo docker run -d --network host --name galaxy-ucenter 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-ucenter:latest
