# Package
aws cloudformation package \
  --template ./cf.yaml \
  --s3-bucket projectkitty \
  --output-template-file ./packaged-template.yaml \
  --region us-west-2 \
  --profile bt

# Deploy package
aws cloudformation deploy \
--template-file ./packaged-template.yaml \
--stack-name ProjectKitty \
--region us-west-2 \
--profile bt \
--capabilities CAPABILITY_IAM

# Create Stack
aws cloudformation create-stack \
  --region us-west-2 \
  --stack-name ProjectKitty \
  --template-body file://src/CloudFormation/cf.yaml \
  --capabilities CAPABILITY_IAM \
  --profile bt

#########################################Tables
# Create Tables Only
aws cloudformation create-stack \
  --region us-west-2 \
  --stack-name ProjectKittyTablesOnly \
  --template-body file://cfTables.yaml \
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

# Package
aws cloudformation package \
  --template ./cfTest.yaml \
  --s3-bucket projectkitty \
  --output-template-file ./packaged-template-test.yaml \
  --region us-west-2 \
  --profile bt

# Deploy
aws cloudformation deploy \
  --template-file ./packaged-template-test.yaml \
  --stack-name ProjectKittyTest10 \
  --region us-west-2 \
  --profile bt \
  --capabilities CAPABILITY_IAM
