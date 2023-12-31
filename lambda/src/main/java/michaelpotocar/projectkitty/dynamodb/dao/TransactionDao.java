package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;

import java.util.List;

public class TransactionDao {

    public static List<Transaction> get(String accountId) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        DynamoDBQueryExpression<Transaction> queryExpression = (new DynamoDBQueryExpression()).withHashKeyValues(transaction);
        List<Transaction> transactions = ddbMapper.query(Transaction.class, queryExpression);
        return transactions;
    }

    public static void save(Transaction transaction) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        ddbMapper.save(transaction);
    }
}
