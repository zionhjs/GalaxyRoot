REGISTRY="688559712485.dkr.ecr.us-west-1.amazonaws.com"
mvn clean package
for project in `ls | grep galaxy `;do
  aws ecs create-repository --repository-name $project
  docker build -t $REGISTRY/$project --build-arg PROJECT=$project .
  docker push $REGISTRY/$project
done