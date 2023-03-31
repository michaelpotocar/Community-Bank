
package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDao {

    public AccountDao() {
    }

    public static List<Account> getAccounts(Long customerId) {

        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        Account account = new Account();
        account.setCustomerId(customerId);
        DynamoDBQueryExpression<Account> queryExpression = (new DynamoDBQueryExpression()).withHashKeyValues(account);
        List<Account> accounts = ddbMapper.query(Account.class, queryExpression);

        return accounts;

    }

    public static Account getAccount(Long customerId, String accountId) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        Account account = ddbMapper.load(Account.class, customerId, accountId);
        return account;
    }

    public static void saveAccount(Account account) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        ddbMapper.save(account);
    }

}