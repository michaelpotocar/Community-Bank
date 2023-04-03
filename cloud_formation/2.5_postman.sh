api_id=`/usr/local/bin/aws apigateway get-rest-apis \
  --profile bt \
  --region us-west-2 \
  | /opt/homebrew/bin/jq -r '.items[] | select(.name == "ProjectKitty") | .id'`

echo $api_id > '/Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/frontend/public/.ignore/api_id'

api_message="API ID: ${api_id}"
echo "${api_message}"

postman_api_key=`cat /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/.ignore/postman_api_key`
postman_collection=`cat /Users/michaelpotocar/Developer/Bloomtech/bd-team-project-project-kitty/.ignore/postman_collection`

/usr/local/bin/postman login --with-api-key ${postman_api_key} \
  > /dev/null
/usr/local/bin/postman collection run ${postman_collection} \
  --global-var api_id=${api_id}
