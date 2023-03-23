import { DynamoDBClient, ScanCommand, DeleteItemCommand, DescribeTableCommand } from "@aws-sdk/client-dynamodb";
import { fromIni } from "@aws-sdk/credential-provider-ini";
import { fromEnv } from "@aws-sdk/credential-provider-env";
import { loadSharedConfigFiles } from '@aws-sdk/shared-ini-file-loader';

let credentials;
if (process.env.AWS_LAMBDA_FUNCTION_NAME) {
    credentials = fromEnv();
} else {
    let profile = 'bt';
    credentials = {
        credentials: fromIni({ profile }),
        region: (await loadSharedConfigFiles()).configFile?.[profile]?.region,
    };
}

const tables = ['Customers', 'Accounts', 'Transactions', 'PeerToPeerTransactions'];
const ddbClient = new DynamoDBClient(credentials);
let params;
let key;
let items;
let item;
let table;
let schema;
let index;
let promises = [];

export const handler = async function (event, context) {
    for (table of tables) {

        params = { TableName: table };
        schema = (await ddbClient.send(new DescribeTableCommand(params))).Table.KeySchema;
        items = (await ddbClient.send(new ScanCommand(params))).Items;

        for (item of items) {
            key = {};
            for (index of schema) {
                key[index.AttributeName] = item[index.AttributeName];
            }
            params = {
                TableName: table,
                Key: key
            };

            promises.push(new Promise(resolve => {
                ddbClient.send(new DeleteItemCommand(params));
                resolve();
            }));
        };
    }

    await Promise.allSettled(promises)
        .then(console.log('Deleted All'))
        .catch(err => console.log(err));

};
