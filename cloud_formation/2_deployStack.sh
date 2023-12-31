clear

echo 'Deleting Existing Stack'

aws cloudformation delete-stack \
  --region us-west-2 \
  --stack-name ProjectKitty \
  --profile bt

echo 'Building Artifacts'

rm /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/lambda/build/distributions/lambda-1.0-SNAPSHOT.zip
/Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/lambda/gradlew \
  -b /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/lambda/build.gradle buildZip \
  > /dev/null

zip -j /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/populateTables/populateTables.zip \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/populateTables/index.mjs \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/populateTables/cfn-response.mjs \
  > /dev/null

zip -j /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/emptyTables/emptyTables.zip \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/emptyTables/index.mjs \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/emptyTables/cfn-response.mjs \
  > /dev/null

echo 'Uploading to S3'

aws s3 cp \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/ddbTableData/Customers.json \
 s3://projectkitty \
  --profile bt \
  > /dev/null

aws s3 cp \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/ddbTableData/Accounts.json \
 s3://projectkitty \
  --profile bt \
  > /dev/null

aws s3 cp \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/ddbTableData/Transactions.json \
 s3://projectkitty \
  --profile bt \
  > /dev/null

aws s3 cp \
 /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/lambda/build/distributions/lambda-1.0-SNAPSHOT.zip \
 s3://projectkitty \
  --profile bt \
  > /dev/null

aws s3 cp \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/populateTables/populateTables.zip \
  s3://projectkitty \
  --profile bt \
  > /dev/null

aws s3 cp \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/emptyTables/emptyTables.zip \
  s3://projectkitty \
  --profile bt \
  > /dev/null

aws s3 cp \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/cf.yaml \
  s3://projectkitty \
  --profile bt \
  > /dev/null

aws s3 cp \
  /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/swagger.yaml \
  s3://projectkitty \
  --profile bt \
  > /dev/null

echo 'Confirming Stack Deletion'

aws cloudformation wait stack-delete-complete \
  --stack-name ProjectKitty \
  --profile bt \
  --region us-west-2 \
  > /dev/null

echo 'Creating Stack'

aws cloudformation create-stack \
  --stack-name ProjectKitty \
  --template-url https://projectkitty.s3.us-west-2.amazonaws.com/cf.yaml \
  --capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND \
  --profile bt \
  > /dev/null

echo 'Confirming Stack Creation'

aws cloudformation wait stack-create-complete \
  --stack-name ProjectKitty \
  --profile bt \
  --region us-west-2  
  > /dev/null

echo 'Stack Created'

echo ''

/Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/cloud_formation/2.5_postman.sh

echo ''
