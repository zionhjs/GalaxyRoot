set -e
REGISTRY="688559712485.dkr.ecr.us-west-1.amazonaws.com"
mvn clean package -B
for project in `ls | grep galaxy `;do
  aws ecr describe-repositories --repository-names ${project} || aws ecr create-repository --repository-name ${project}
  docker build -t $REGISTRY/$project --build-arg PROJECT=$project .
  docker push $REGISTRY/$project
done