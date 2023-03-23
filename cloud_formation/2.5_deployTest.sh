aws s3 cp \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/include_test.yaml \
  s3://projectkitty \
  --profile bt

aws s3 cp \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/cf_test.yaml \
  s3://projectkitty \
  --profile bt

aws cloudformation create-stack \
  --stack-name cftest \
  --template-url https://projectkitty.s3.us-west-2.amazonaws.com/cf_test.yaml \
  --capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND \
  --profile bt
