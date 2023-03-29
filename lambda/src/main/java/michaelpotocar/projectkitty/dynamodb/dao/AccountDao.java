
package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.model.Account;

public class AccountDao {

    public AccountDao() {
    }

    public static Account getAccount(Long accountNumber) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        Account account = ddbMapper.load(Account.class, accountNumber);
        return account;
    }

    public static void saveAccount(Account account) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        ddbMapper.save(account);
    }

}
