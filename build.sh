REGISTRY="688559712485.dkr.ecr.us-west-1.amazonaws.com"
mvn clean package
for project in `ls | grep galaxy `;do
  aws ecr create-repository --repository-name $project --region=us-west-1
  docker build -t $REGISTRY/$project --build-arg PROJECT=$project .
  docker push $REGISTRY/$project
done