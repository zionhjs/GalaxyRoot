#!/bin/bash

sleep 210

sudo docker run -p 9800:9800 -d --name galaxy-upload 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-upload:latest

sudo !94