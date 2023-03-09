import { S3Client, GetObjectCommand } from "@aws-sdk/client-s3";
import { DynamoDBClient, BatchWriteItemCommand } from "@aws-sdk/client-dynamodb";
import { DynamoDBDocumentClient } from "@aws-sdk/lib-dynamodb";

const { s3Client, ddbDocClient } = await import("./config.js").then((local) => {
    return {
        s3Client: new S3Client(local.config),
        ddbDocClient: DynamoDBDocumentClient.from(new DynamoDBClient(local.config))
    };
}).catch(() => {
    return {
        s3Client: new S3Client({}),
        ddbDocClient: DynamoDBDocumentClient.from(new DynamoDBClient({ region: 'us-west-2' }))
    };
});

const tableData = {
    ProjectKittyCustomers: {
        Bucket: "projectkitty",
        Key: "ProjectKittyCustomers.json"
    },
    ProjectKittyAccounts: {
        Bucket: "projectkitty",
        Key: "ProjectKittyAccounts.json"
    },
    ProjectKittyTransactions: {
        Bucket: "projectkitty",
        Key: "ProjectKittyTransactions.json"
    },
};

export const handler = async function (event, context) {
    for (const table in tableData) {
        const bucket = tableData[table].Bucket;
        const key = tableData[table].Key;

        let str;
        try {
            const response = await s3Client.send(new GetObjectCommand({ Bucket: bucket, Key: key }));
            str = await response.Body.transformToString();
            console.log(key, 'loaded Succcessfully');
        } catch (err) {
            console.error(err);
            process.exit(0);
        }
        const entries = JSON.parse(str);

        const batchSize = 25;
        const batches = [];

        if (table == 'ProjectKittyCustomers') {
            for (let i = 0; i < entries.length / batchSize; i++) {
                const filtered = entries.filter(({ }, index) => index >= batchSize * i && index < batchSize * (i + 1));
                const mapped = filtered.map(entry => {
                    const mappedFriends = entry.friends.map(friend => {
                        return {
                            "N": String(friend),
                        };
                    });
                    return {
                        "PutRequest": {
                            "Item": {
                                id: { N: String(entry.id) },
                                firstName: { S: String(entry.firstName) },
                                lastName: { S: String(entry.lastName) },
                                friends: { L: mappedFriends },
                            }
                        }
                    };
                });
                batches.push(mapped);
            }
        }

        if (table == 'ProjectKittyAccounts') {
            for (let i = 0; i < entries.length / batchSize; i++) {
                const filtered = entries.filter(({ }, index) => index >= batchSize * i && index < batchSize * (i + 1));
                const mapped = filtered.map(entry => {
                    return {
                        "PutRequest": {
                            "Item": {
                                accountNumber: { N: String(entry.accountNumber) },
                                routingNumber: { N: String(entry.routingNumber) },
                                customerId: { N: String(entry.customerId) },
                                nickname: { S: String(entry.nickname) },
                                type: { S: String(entry.type) },
                            }
                        }
                    };
                });
                batches.push(mapped);
            }
        }

        if (table == 'ProjectKittyTransactions') {
            for (let i = 0; i < entries.length / batchSize; i++) {
                const filtered = entries.filter(({ }, index) => index >= batchSize * i && index < batchSize * (i + 1));
                const mapped = filtered.map(entry => {
                    return {
                        "PutRequest": {
                            "Item": {
                                accountNumber: { N: String(entry.accountNumber) },
                                submittedDateTime: { N: String(entry.submittedDateTime) },
                                completedDateTime: { N: String(entry.completedDateTime) },
                                amount: { N: String(entry.amount) },
                                memo: { S: String(entry.memo) },
                            }
                        }
                    };
                });
                batches.push(mapped);
            }
        }

        for (const batch in batches) {
            const params = {
                RequestItems: {
                    [table]: batches[batch]
                }
            };

            try {
                await ddbDocClient.send(new BatchWriteItemCommand(params));
                console.log(table, 'batch persisted Succcessfully');
            } catch (err) {
                console.error(err);
                process.exit(0);
            }
        }
    }
};
