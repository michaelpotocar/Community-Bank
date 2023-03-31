package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import java.util.List;

import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;

public class TransactionDao {

    public TransactionDao() {
    }

    public static List<Transaction> getAccountTransactions(String accountId) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        DynamoDBQueryExpression<Transaction> queryExpression = (new DynamoDBQueryExpression()).withHashKeyValues(transaction);
        List<Transaction> transactions = ddbMapper.query(Transaction.class, queryExpression);
        return transactions;
    }

    public static void saveTransaction(Transaction transaction) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        ddbMapper.save(transaction);
    }
}
