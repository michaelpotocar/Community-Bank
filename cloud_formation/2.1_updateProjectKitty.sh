aws s3 cp \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/cf.yaml \
  s3://projectkitty \
  --profile bt

aws s3 cp \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/swagger.yaml \
  s3://projectkitty \
  --profile bt

aws cloudformation update-stack \
  --stack-name ProjectKitty \
  --template-url https://projectkitty.s3.us-west-2.amazonaws.com/cf.yaml \
  --capabilities CAPABILITY_IAM \
  --profile bt
