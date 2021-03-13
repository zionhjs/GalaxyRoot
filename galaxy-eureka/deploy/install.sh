#!/bin/bash
HOME=/home/ec2-user

WORK=/home/ec2-user/jar

NAME=galaxy-eureka

IMAGE_TAG=$(echo $RANDOM)

date=$(date +%Y-%m-%d)

REPOSITORY_URL=688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-eureka:latest

cd $WORK

sudo mv *.jar $HOME/${NAME}.jar

cd $HOME

sudo docker build -t $REPOSITORY_URI .

sudo docker tag $REPOSITORY_URI:latest $REPOSITORY_URI:${date}_${IMAGE_TAG}

sudo docker push $REPOSITORY_URI:latest

sudo docker push $REPOSITORY_URI:$IMAGE_TAG

