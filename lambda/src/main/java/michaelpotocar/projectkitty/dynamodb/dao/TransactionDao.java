//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import java.util.List;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;

public class TransactionDao {
    private static final DefaultAWSCredentialsProviderChain credentialsProvider = new DefaultAWSCredentialsProviderChain();
    private static final ProfileCredentialsProvider localCredentialsProvider = new ProfileCredentialsProvider("bt");
    private static final AmazonDynamoDB ddbClient;
    private static final DynamoDBMapper ddbMapper;

    public TransactionDao() {
    }

    public static List<Transaction> getAccountTransactions(Long accountNumber) {
        Transaction t = new Transaction();
        t.setAccountNumber(accountNumber);
        DynamoDBQueryExpression<Transaction> queryExpression = (new DynamoDBQueryExpression()).withHashKeyValues(t);
        List<Transaction> transactions = ddbMapper.query(Transaction.class, queryExpression);
        return transactions;
    }

    public static void saveTransaction(Transaction transaction) {
        ddbMapper.save(transaction);
    }

    static {
        if (System.getenv("AWS_EXECUTION_ENV") != null) {
            ddbClient = (AmazonDynamoDB)((AmazonDynamoDBClientBuilder)((AmazonDynamoDBClientBuilder)AmazonDynamoDBClientBuilder.standard().withCredentials(credentialsProvider)).withRegion(Regions.US_WEST_2)).build();
        } else {
            ddbClient = (AmazonDynamoDB)((AmazonDynamoDBClientBuilder)((AmazonDynamoDBClientBuilder)AmazonDynamoDBClientBuilder.standard().withCredentials(localCredentialsProvider)).withRegion(Regions.US_WEST_2)).build();
        }

        ddbMapper = new DynamoDBMapper(ddbClient);
    }
}
