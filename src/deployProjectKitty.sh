aws cloudformation create-stack \
  --region us-west-2 \
  --stack-name ProjectKitty \
  --template-body file://CloudFormation/ProjectKittyCloudFormationTmp.yaml \
  --capabilities CAPABILITY_IAM \
  --profile bt
aws cloudformation package \
  --template ./src/CloudFormation/ProjectKittyCloudFormation.yaml \
  --s3-bucket projectkitty \
  --output-template-file ./src/CloudFormation/packaged-template.json \
  --region us-west-2 \
  --profile bt
