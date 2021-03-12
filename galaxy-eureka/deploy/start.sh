#!/bin/bash
NAME=galaxy-eureka

REPOSITORY_URL=688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-eureka:latest

sudo docker run --name $NAME $REPOSITORY_URL
