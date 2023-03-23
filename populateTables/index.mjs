import { S3Client, GetObjectCommand } from '@aws-sdk/client-s3';
import { DynamoDBClient, BatchWriteItemCommand } from '@aws-sdk/client-dynamodb';
import { fromIni } from '@aws-sdk/credential-provider-ini';
import { fromEnv } from '@aws-sdk/credential-provider-env';
import { loadSharedConfigFiles } from '@aws-sdk/shared-ini-file-loader';
import * as cfnResponse from './cfn-response.mjs';

const tableData = [
    {
        bucket: 'projectkitty',
        key: 'Customers.json',
        write: obj => { customers = obj; },
    },
    {
        bucket: 'projectkitty',
        key: 'Accounts.json',
        write: obj => { accounts = obj; },
    },
    {
        bucket: 'projectkitty',
        key: 'Transactions.json',
        write: obj => { transactions = obj; },
    },
];

let customers, accounts, transactions;

let s3Promises = [];
let batchSize = 1;
let ddbPromises = [];

export const handler = async function (event, context) {
    ////////////////////////Log Event
    if (event) { console.log(JSON.stringify(event)); }

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

    ////////////////////////Wait for Data
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

    ////////////////////////Link Data

    let transactionAccounts = accounts.filter(account => account.type != 'savings' && account.type != 'external');
    let checkingAccounts = accounts.filter(account => account.type == 'checking');
    let creditAccounts = accounts.filter(account => account.type == 'credit');
    let savingsAccounts = accounts.filter(account => account.type == 'savings');


    //Index objects
    customers.forEach((customer, index) => {
        customer.index = index;
    });
    accounts.forEach((account, index) => {
        account.index = index;

        let i = 0;
        let n = account.nickname.charAt(0).toUpperCase() + account.nickname.slice(1);
        while (n.indexOf(' ', i) != -1) {
            n = n.slice(0, n.indexOf(' ', i) + 1) + n.charAt(n.indexOf(' ', i) + 1).toUpperCase() + n.slice(n.indexOf(' ', i) + 2);
            i = n.indexOf(' ', i) + 1;
        }
        account.nickname = n;

    });
    transactionAccounts.forEach((account, index) => {
        account.secondaryIndex = index;
    });
    transactions.forEach((transaction, index) => {
        transaction.index = index;
    });

    //Link
    for (let transactionAccount of transactionAccounts) {
        transactionAccount.transactions = transactions.filter(transaction => transaction.index % transactionAccounts.length == transactionAccount.secondaryIndex);
        for (let transaction of transactionAccount.transactions) {
            transaction.accountNumber = transactionAccount.accountNumber;
        }
    }

    //Correct transaction amounts and memos 
    for (let account of checkingAccounts) {
        for (let transaction of account.transactions) {
            transaction.amount *= -1;
        }
    }

    for (let account of creditAccounts) {
        for (let transaction of account.transactions) {
            if (transaction.amount < 0) {
                transaction.memo = 'Payment - Thank You';
            }
        }
    }

    for (let account of checkingAccounts) {
        account.transactions.sort((a, b) => { return a.submittedDateTime - b.submittedDateTime; });
        let balance = 0;
        let newBalance = 0;
        account.transactions.forEach((transaction, index) => {
            newBalance = balance + transaction.amount;
            if (newBalance < 0) {
                let delta = -newBalance;
                let amount = transaction.amount + Math.ceil(delta / 100) * 100 * (index + 1) + 100;
                let memo = amount > 0 ? 'Balance Transfer' : 'Payment - Thank You';
                transaction.amount = amount;
                transaction.memo = memo;
                balance += amount;
            } else {
                balance += transaction.amount;
            }
        });

        // account.transactions.sort((a, b) => { return a.submittedDateTime - b.submittedDateTime; });
        // balance = 0;
        // account.transactions.forEach((transaction) => {
        //     balance += transaction.amount;
        //     console.log(balance);
        // });
    }

    for (let account of creditAccounts) {
        account.transactions.sort((a, b) => { return a.submittedDateTime - b.submittedDateTime; });
        let balance = 0;
        let newBalance = 0;
        account.transactions.forEach((transaction, index) => {

            newBalance = balance + transaction.amount;
            if (newBalance < 0) {
                let amount = .5 * account.creditLimit + Math.random() * 200 - 100 - balance;
                let memo = amount > 0 ? 'Balance Transfer' : 'Payment - Thank You';
                transaction.amount = (Math.round(amount * 100) / 100);
                transaction.memo = memo;
                balance += amount;
            } else if (newBalance > account.creditLimit) {
                let amount = .5 * account.creditLimit + Math.random() * 200 - 100 - balance;
                let memo = amount > 0 ? 'Balance Transfer' : 'Payment - Thank You';
                transaction.amount = (Math.round(amount * 100) / 100);
                transaction.memo = memo;
                balance += amount;
            } else {
                balance += transaction.amount;
            }
        });

        // account.transactions.sort((a, b) => { return a.submittedDateTime - b.submittedDateTime; });
        // balance = 0;
        // console.log(`limit: ${account.creditLimit}`);
        // account.transactions.forEach((transaction) => {
        //     balance += transaction.amount;
        //     console.log(balance);
        // });
    }

    for (let account of savingsAccounts) {

        let amount = (account.index + 1) * 100;
        let submittedDateTime = 1640995200 + ((account.index + 1) / (accounts.length + 1)) * 86400;
        let completedDateTime = Math.ceil(submittedDateTime / 86400) * 86400;

        let newTransaction = {
            accountNumber: account.accountNumber,
            submittedDateTime: submittedDateTime,
            completedDateTime: completedDateTime,
            amount: amount,
            memo: 'Deposit',
        };

        transactions.push(newTransaction);
        account.transactions = [newTransaction];
    }

    for (let account of accounts) {
        account.balance = (Math.round((account.transactions ?? []).reduce((accumulator, transaction) => {
            transaction.accountNumber = account.accountNumber;
            return accumulator + transaction.amount;
        }, 0) * 100) / 100);

    }
    for (let customer of customers) {
        customer.contacts = customers.filter(contact => Math.abs(customer.id - contact.id) < 200000000 && customer.id != contact.id);
        customer.accounts = accounts.filter(account => (account.accountNumber % customers.length) == customer.index);
        for (let account of customer.accounts) {
            account.customerId = customer.id;
        }
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

        let params = { RequestItems: { 'Transactions': batchTransactions } };
        ddbPromises.push(ddbClient.send(new BatchWriteItemCommand(params)));
    }

    ////////////////////////Accounts
    //Transaction Accounts
    let mappedAccounts = accounts.map(account => {
        let mappedAccount = {
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
        if (account.type == 'credit') {
            mappedAccount.PutRequest.Item.creditLimit = { N: String(account.creditLimit) };
        }
        return mappedAccount;
    });

    //Create DynamoBD Promise
    for (let i = 0; i < Math.ceil(mappedAccounts.length / batchSize); i++) {
        const batchAccounts = mappedAccounts.filter((({ }, j) =>
            j >= (batchSize * i) && j < (batchSize * (i + 1))
        ));

        let params = { RequestItems: { 'Accounts': batchAccounts } };
        ddbPromises.push(ddbClient.send(new BatchWriteItemCommand(params)));
    }

    ////////////////////////Customers
    //Connect Tables
    let mappedCustomers = customers.map((customer) => {
        let mappedContacts = customer.contacts.map(contact => {
            return {
                M: {
                    id: { N: String(contact.id) },
                    firstName: { S: String(contact.firstName) },
                    lastName: { S: String(contact.lastName) },
                }
            };
        });

        let mappedAccounts = customer.accounts.map(account => {
            let mappedAccount = {
                M: {
                    accountNumber: { N: String(account.accountNumber) },
                    nickname: { S: String(account.nickname) },
                    type: { S: String(account.type) },
                }
            };
            if (account.type != 'external') {
                mappedAccount.M.balance = { N: String(account.balance) };
            }
            return mappedAccount;
        });

        let mappedCustomer = {
            PutRequest: {
                Item: {
                    id: { N: String(customer.id) },
                    firstName: { S: String(customer.firstName) },
                    lastName: { S: String(customer.lastName) },
                }
            }
        };
        if (customer.contacts.length > 0) {
            mappedCustomer.PutRequest.Item.contacts = { L: mappedContacts };
        }
        if (customer.accounts.length > 0) {
            mappedCustomer.PutRequest.Item.accounts = { L: mappedAccounts };
        }
        return mappedCustomer;
    });

    //Create DynamoBD Promise
    for (let i = 0; i < Math.ceil(mappedCustomers.length / batchSize); i++) {
        const batchCustomers = mappedCustomers.filter((({ }, j) =>
            j >= (batchSize * i) && j < (batchSize * (i + 1))
        ));

        let params = { RequestItems: { 'Customers': batchCustomers } };
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