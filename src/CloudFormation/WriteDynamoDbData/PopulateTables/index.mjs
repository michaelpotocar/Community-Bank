import { S3Client, GetObjectCommand } from '@aws-sdk/client-s3';
import { DynamoDBClient, BatchWriteItemCommand } from '@aws-sdk/client-dynamodb';
import { fromIni } from '@aws-sdk/credential-provider-ini';
import { fromEnv } from '@aws-sdk/credential-provider-env';
import { loadSharedConfigFiles } from '@aws-sdk/shared-ini-file-loader';
import * as cfnResponse from './cfn-response.mjs';

let customers, accounts, transactions;

let s3Promises = [];
const batchSize = 25;
let ddbPromises = [];

const tableData = [
    {
        bucket: 'projectkitty',
        key: 'ProjectKittyCustomers.json',
        write: obj => { customers = obj; },
    },
    {
        bucket: 'projectkitty',
        key: 'ProjectKittyAccounts.json',
        write: obj => { accounts = obj; },
    },
    {
        bucket: 'projectkitty',
        key: 'ProjectKittyTransactions.json',
        write: obj => { transactions = obj; },
    },
];

export const handler = async function (event, context) {
    ////////////////////////Log Event
    if (event) {
        console.log(JSON.stringify(event));
    }

    ////////////////////////Exit if CloudFormation Delete
    if (event?.RequestType == 'Delete') {
        await cfnResponse.send(event, context, cfnResponse.SUCCESS, {})
            .catch(err => { console.log(JSON.stringify(err)); });
        return ('Delete Successful');
    }

    ////////////////////////Instanciate Clients
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
    const s3Client = new S3Client(credentials);
    const ddbClient = new DynamoDBClient(credentials);

    ////////////////////////Gather json data
    for (const entry of tableData) {
        s3Promises.push(
            s3Client.send(new GetObjectCommand({ Bucket: entry.bucket, Key: entry.key }))
                .then(newResult => newResult.Body.transformToString())
                .then(finalResult => {
                    entry.write(JSON.parse(finalResult));
                })
        );
    }

    await Promise.all(s3Promises)
        .then(console.log('json read from s3'))
        .catch((err) => {
            console.error(err);
            if (event?.RequestType != undefined) {
                cfnResponse.send(event, context, cfnResponse.FAILED, {});
            }
        }).catch(err => {
            console.log(JSON.stringify(err));
            return ('Failure when reading from s3');
        });

    ////////////////////////Customers
    //Connect Tables
    let mappedCustomers = customers.map((customer, i) => {
        let relatedCustomers = customers.filter(contact => Math.abs(customer.id - contact.id) < 200000000 && customer.id != contact.id);
        let mappedContacts = relatedCustomers.map(contact => {
            return {
                N: String(contact.id),
            };
        });

        let relatedAccounts = accounts.filter(account => (account.accountNumber % customers.length) == i);
        let mappedAccounts = relatedAccounts.map(account => {
            account.customerId = customer.id;
            return {
                M: {
                    accountNumber: { N: String(account.accountNumber) },
                    nickname: { S: String(account.nickname) },
                    type: { S: String(account.type) },
                }
            };
        });

        return {
            PutRequest: {
                Item: {
                    id: { N: String(customer.id) },
                    firstName: { S: String(customer.firstName) },
                    lastName: { S: String(customer.lastName) },
                    contacts: { L: mappedContacts },
                    accounts: { L: mappedAccounts },
                }
            }
        };
    });

    //Create DynamoBD Promise
    for (let i = 0; i < Math.ceil(mappedCustomers.length / batchSize); i++) {
        const batchCustomers = mappedCustomers.filter((({ }, j) =>
            j >= (batchSize * i) && j < (batchSize * (i + 1))
        ));

        let params = { RequestItems: { 'ProjectKittyCustomers': batchCustomers } };
        ddbPromises.push(ddbClient.send(new BatchWriteItemCommand(params)));
    }

    ////////////////////////Accounts
    //Transaction Accounts
    let transactionAccounts = accounts.filter(account => account.type != 'savings' && account.type != 'external');
    let mappedtransactionAccounts = transactionAccounts.map((account, i) => {

        //Find Related Transactions and Balance
        let relatedTransactions = transactions.filter(({ }, j) => j % transactionAccounts.length == i);
        account.balance = relatedTransactions.reduce((accumulator, transaction) => {
            transaction.accountNumber = account.accountNumber;
            return accumulator + transaction.amount;
        }, 0);

        return {
            PutRequest: {
                Item: {
                    accountNumber: { N: String(account.accountNumber) },
                    routingNumber: { N: String(account.routingNumber) },
                    customerId: { N: String(account.customerId) },
                    nickname: { S: String(account.nickname) },
                    type: { S: String(account.type) },
                    balance: { N: String(account.balance) },
                }
            }
        };
    });

    //Savings Accounts
    let savingsAccounts = accounts.filter(account => account.type == 'savings');
    let mappedSavingsAccounts = savingsAccounts.map((account, i) => {
        return {
            PutRequest: {
                Item: {
                    accountNumber: { N: String(account.accountNumber) },
                    routingNumber: { N: String(account.routingNumber) },
                    customerId: { N: String(account.customerId) },
                    nickname: { S: String(account.nickname) },
                    type: { S: String(account.type) },
                    balance: { N: String(0) },
                }
            }
        };
    });

    //External Accounts
    let externalAccounts = accounts.filter(account => account.type == 'external');
    let mappedExternalAccounts = externalAccounts.map((account, i) => {
        return {
            PutRequest: {
                Item: {
                    accountNumber: { N: String(account.accountNumber) },
                    routingNumber: { N: String(account.routingNumber) },
                    customerId: { N: String(account.customerId) },
                    nickname: { S: String(account.nickname) },
                    type: { S: String(account.type) },
                }
            }
        };
    });

    //Create DynamoBD Promise
    let mappedAccounts = mappedtransactionAccounts.concat(mappedSavingsAccounts).concat(mappedExternalAccounts);
    for (let i = 0; i < Math.ceil(mappedAccounts.length / batchSize); i++) {
        const batchAccounts = mappedAccounts.filter((({ }, j) =>
            j >= (batchSize * i) && j < (batchSize * (i + 1))
        ));

        let params = { RequestItems: { 'ProjectKittyAccounts': batchAccounts } };
        ddbPromises.push(ddbClient.send(new BatchWriteItemCommand(params)));
    }

    ////////////////////////Transactions
    //Connect Tables
    let mappedTransactions = transactions.map((transaction, i) => {
        return {
            PutRequest: {
                Item: {
                    accountNumber: { N: String(transaction.accountNumber) },
                    submittedDateTime: { N: String(transaction.submittedDateTime) },
                    completedDateTime: { N: String(transaction.completedDateTime) },
                    amount: { N: String(transaction.amount) },
                    memo: { S: String(transaction.memo) },
                }
            }
        };
    });

    //Create DynamoBD Promise
    for (let i = 0; i < Math.ceil(mappedTransactions.length / batchSize); i++) {
        const batchTransactions = mappedTransactions.filter((({ }, j) =>
            j >= (batchSize * i) && j < (batchSize * (i + 1))
        ));

        let params = { RequestItems: { 'ProjectKittyTransactions': batchTransactions } };
        ddbPromises.push(ddbClient.send(new BatchWriteItemCommand(params)));
    }

    ////////////////////////Wait for DynamoDB
    await Promise.allSettled(ddbPromises)
        .then(results => {
            const rejectedDdb = results.filter(result => result.status == 'rejected');
            if (rejectedDdb.length > 0) {
                throw new Error(JSON.stringify(rejectedDdb.map(result => result.reason)));
            }
            console.log('dynamodb written successfully');
        })
        .catch((err) => {
            console.error(err);
            if (event?.RequestType != undefined) {
                cfnResponse.send(event, context, cfnResponse.FAILED, {});
            }
        }).catch(err => {
            console.log(JSON.stringify(err));
            return ('Failure when writing to DynamoDB');
        });

    ////////////////////////Send Success to CloudFormation
    if (event?.RequestType != undefined) {
        await cfnResponse.send(event, context, cfnResponse.SUCCESS, {})
            .catch(err => { console.log(JSON.stringify(err)); });
    }
};