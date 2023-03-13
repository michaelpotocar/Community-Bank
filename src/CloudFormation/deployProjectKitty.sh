# Create Stack
rm  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/src/CloudFormation/WriteDynamoDbLambda.zip
zip -j /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/src/CloudFormation/WriteDynamoDbLambda.zip \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/src/CloudFormation/WriteDynamoDbData/PopulateTables/index.mjs \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/src/CloudFormation/WriteDynamoDbData/PopulateTables/cfn-response.mjs
aws s3 cp \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/src/CloudFormation/WriteDynamoDbLambda.zip \
 s3://projectkitty \
 --profile bt
aws s3 cp \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/src/CloudFormation/cf.yaml \
 s3://projectkitty \
 --profile bt
aws cloudformation create-stack \
 --stack-name ProjectKitty \
 --template-url https://projectkitty.s3.us-west-2.amazonaws.com/cf.yaml \
  --capabilities CAPABILITY_IAM \
 --profile bt
# Delete Stack
aws cloudformation delete-stack \
  --region us-west-2 \
  --stack-name ProjectKitty \
  --profile bt
#########################################Tables
# Create Stack
aws cloudformation create-stack \
  --region us-west-2 \
  --stack-name ProjectKittyTablesOnly \
  --template-body file://src/CloudFormation/cfTables.yaml \
  --capabilities CAPABILITY_IAM \
  --profile bt

#########################################Test
# create-stack
aws cloudformation create-stack \
  --region us-west-2 \
  --stack-name ProjectKittyTest \
  --template-body file://src/CloudFormation/cfTest.yaml \
  --capabilities CAPABILITY_IAM \
  --profile bt
