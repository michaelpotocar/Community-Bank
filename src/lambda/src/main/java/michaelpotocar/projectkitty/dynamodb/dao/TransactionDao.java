package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;

import java.util.List;

public class TransactionDao {

    private static final DefaultAWSCredentialsProviderChain credentialsProvider;
    private static final ProfileCredentialsProvider localCredentialsProvider;
    private static final AmazonDynamoDB ddbClient;
    private static final DynamoDBMapper ddbMapper;

    static {
        credentialsProvider = new DefaultAWSCredentialsProviderChain();
        localCredentialsProvider = new ProfileCredentialsProvider("bt");

        if (System.getenv("AWS_EXECUTION_ENV") != null) {
            ddbClient = AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(credentialsProvider)
                    .withRegion(Regions.US_WEST_2)
                    .build();
        } else {
            ddbClient = AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(localCredentialsProvider)
                    .withRegion(Regions.US_WEST_2)
                    .build();
        }
        ddbMapper = new DynamoDBMapper(ddbClient);
    }

    public static List<Transaction> getAccountTransactions(Long accountNumber) {
        Transaction t = new Transaction();
        t.setAccountNumber(accountNumber);
        DynamoDBQueryExpression<Transaction> queryExpression = new DynamoDBQueryExpression<Transaction>()
                .withHashKeyValues(t);

        List<Transaction> transactions = ddbMapper.query(Transaction.class, queryExpression);
        return transactions;
    }

    public static void saveTransaction(Transaction transaction) {
        ddbMapper.save(transaction);
    }
}
