
package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.model.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDao {

    public AccountDao() {
    }

    public static List<Account> getAccounts(Long customerId) {

        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":customerId", new AttributeValue().withN(customerId.toString()));
        DynamoDBQueryExpression<Account> queryExpression = new DynamoDBQueryExpression<Account>()
                .withIndexName("CustomerId")
                .withConsistentRead(false)
                .withKeyConditionExpression("customerId = :customerId")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Account> accounts = ddbMapper.query(Account.class, queryExpression);

        return accounts;

    }

    public static Account getAccount(Long accountNumber, Long routingNumber) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        Account account = ddbMapper.load(Account.class, accountNumber, routingNumber);
        return account;
    }

    public static void saveAccount(Account account) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        ddbMapper.save(account);
    }

}
