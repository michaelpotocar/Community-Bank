package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Customer;

import java.util.List;

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

    public static void saveAccount(Account account) throws Exception {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();

        //Verify Customer Exists
        Customer existingCustomer = ddbMapper.load(Customer.class, account.getCustomerId());
        if (existingCustomer == null) {
            throw new Exception("Error: Customer Does Not Exist");
        }

        //Verify Account Unique
        List<Account> existingAccounts = AccountDao.getAccounts(account.getCustomerId());
        for (Account existingAccount : existingAccounts) {
            if (existingAccount.getAccountId().equals(account.getAccountId())) {
                throw new Exception("Error: Account/Routing Combination Already Exists");
            } else if (existingAccount.getNickname().equals(account.getNickname())) {
                throw new Exception("Error: Nickname Already Exists");
            }
        }

        //Save the record
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression()
                .withExpectedEntry("accountId", new ExpectedAttributeValue().withExists(false));

        ddbMapper.save(account, saveExpression);
    }

}
