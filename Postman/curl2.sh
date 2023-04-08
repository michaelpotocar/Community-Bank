curl -X GET "https://my-api-id.execute-api.us-west-2.amazonaws.com/prod/customers"
curl -X GET "https://my-api-id.execute-api.us-west-2.amazonaws.com/prod/customers/589631133"
curl -X GET "https://my-api-id.execute-api.us-west-2.amazonaws.com/prod/customers/589631133/accounts/590761751601127000000001"
curl -X GET "https://my-api-id.execute-api.us-west-2.amazonaws.com/prod/customers/589631133/accounts/590761751601127000000001/transactions"
curl -X POST "https://my-api-id.execute-api.us-west-2.amazonaws.com/prod/customers/589631133/accounts" -d '{ "asin": "B004CAWKXE", "trackNumber": 2, "queueNext": true }' -H 'Content-Type: application/json'
curl -X POST "https://my-api-id.execute-api.us-west-2.amazonaws.com/prod/customers/589631133/transfer" -d '{ "type": "p2p", "fundingAccountId": "590761751601127000000001", "targetContactId": "637818676", "amount": "1", "memo": "My p2p Transfer" }'
curl -X GET "https://my-api-id.execute-api.us-west-2.amazonaws.com/prod/customers/637818676/p2p"
curl -X PUT "https://my-api-id.execute-api.us-west-2.amazonaws.com/prod/customers/637818676/p2p/1680839991"
