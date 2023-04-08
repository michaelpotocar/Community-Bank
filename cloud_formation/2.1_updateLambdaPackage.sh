clear

echo 'Building Artifacts'

rm /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/lambda/build/distributions/lambda-1.0-SNAPSHOT.zip
/Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/lambda/gradlew \
  -b /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/lambda/build.gradle buildZip \
  > /dev/null

echo 'Uploading to S3'

aws s3 cp \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/lambda/build/distributions/lambda-1.0-SNAPSHOT.zip \
 s3://projectkitty \
  --profile bt \
  > /dev/null

echo 'Updating Lambdas'

aws lambda list-functions --profile bt | \
 jq '.Functions[].FunctionName' | \
 grep -E "Lambda" | \
 xargs -P -I {} sh -c 'aws lambda update-function-code --function-name {} --s3-bucket projectkitty --s3-key lambda-1.0-SNAPSHOT.zip --profile bt > /dev/null'

echo ''

/Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/2.5_postman.sh

echo ''
