#!/bin/bash

sudo docker run -p 9000:9000 9200:9200 -d --name galaxy-eureka 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-eureka:latest
