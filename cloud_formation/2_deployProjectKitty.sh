aws s3 cp \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/ddbTableData/ProjectKittyCustomers.json \
 s3://projectkitty \
 --profile bt

aws s3 cp \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/ddbTableData/ProjectKittyAccounts.json \
 s3://projectkitty \
 --profile bt

aws s3 cp \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/ddbTableData/ProjectKittyTransactions.json \
 s3://projectkitty \
 --profile bt

aws lambda update-function-code \
  --function-name  kittytest \
  --zip-file fileb:///Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/lambda/build/distributions/lambda-1.0-SNAPSHOT.zip \
  --profile bt

aws lambda update-function-code \
  --function-name  kittytest2 \
  --zip-file fileb:///Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/lambda/build/distributions/lambda-1.0-SNAPSHOT.zip \
  --profile bt

aws s3 cp \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/cf.yaml \
 s3://projectkitty \
 --profile bt

aws cloudformation create-stack \
 --stack-name ProjectKitty \
 --template-url https://projectkitty.s3.us-west-2.amazonaws.com/cf.yaml \
  --capabilities CAPABILITY_IAM \
 --profile bt