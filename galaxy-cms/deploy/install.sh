#!/bin/bash
HOME=/home/ec2-user

WORK=/home/ec2-user/jar

NAME=galaxy-cms

IMAGE_TAG=$(echo $RANDOM)

date=$(date +%Y-%m-%d)

cd $WORK

sudo mv *.jar $HOME/${NAME}.jar

cd $HOME

sudo docker build -t 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-cms .

sudo docker tag 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-cms:latest 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-cms:${date}_${IMAGE_TAG}

sudo docker push 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-cms:latest

sudo docker push 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-cms:${date}_$IMAGE_TAG

sudo docker rmi -f 688559712485.dkr.ecr.us-west-1.amazonaws.com/galaxy-cms:${date}_$IMAGE_TAG

