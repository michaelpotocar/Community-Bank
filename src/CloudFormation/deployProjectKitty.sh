aws cloudformation package \
  --template ./ProjectKittyCloudFormation.yaml \
  --s3-bucket projectkitty \
  --output-template-file ./packaged-template.yaml \
  --region us-west-2 \
  --profile bt
#aws cloudformation create-stack \
#  --region us-west-2 \
#  --stack-name ProjectKitty \
#  --template-body file://CloudFormation/ProjectKittyCloudFormation.yaml \
#  --capabilities CAPABILITY_IAM \
#  --profile bt
