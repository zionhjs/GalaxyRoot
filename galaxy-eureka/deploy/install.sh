#!/bin/bash
HOME=/home/ec2-user

WORK=/home/ec2-user/jar

NAME=galaxy-eureka

IMAGE_TAG=$(echo $RANDOM)

date=$(date +%Y-%m-%d)

REPOSITORY_URL=688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-eureka:latest

cd $WORK

sudo rm -f *source*.jar

sudo mv *.jar $HOME/${NAME}

sudo rm -rf ${WORK}/*

sudo docker build -t $REPOSITORY_URI .

docker tag $REPOSITORY_URI:latest $REPOSITORY_URI:${date}_${IMAGE_TAG}

docker push $REPOSITORY_URI:latest

docker push $REPOSITORY_URI:$IMAGE_TAG

