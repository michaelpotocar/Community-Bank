
package michaelpotocar.projectkitty;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.AccountStub;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.dynamodb.model.CustomerStub;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;
import michaelpotocar.projectkitty.providers.GetCustomerAccountProvider;
import michaelpotocar.projectkitty.providers.GetCustomerAccountsProvider;
import michaelpotocar.projectkitty.providers.GetCustomersProvider;
import michaelpotocar.projectkitty.providers.GetAccountTransactionsProvider;
import michaelpotocar.projectkitty.requests.GetCustomerAccountsRequest;
import michaelpotocar.projectkitty.requests.GetCustomerAccountRequest;
import michaelpotocar.projectkitty.requests.GetCustomersRequest;
import michaelpotocar.projectkitty.requests.GetAccountTransactionsRequest;
import michaelpotocar.projectkitty.results.GetCustomerAccountsResult;
import michaelpotocar.projectkitty.results.GetCustomerAccountResult;
import michaelpotocar.projectkitty.results.GetCustomersResult;
import michaelpotocar.projectkitty.results.GetAccountTransactionsResult;

public class Main {

    public Main() {
    }

    public static void main(String[] args) {
        getCustomerAccount();
    }

    public static void getCustomerAccounts() {
        GetCustomerAccountsRequest request = new GetCustomerAccountsRequest(126879020L);
        GetCustomerAccountsResult result = (new GetCustomerAccountsProvider()).handleRequest(request, null);
    }

    public static void getCustomerAccount() {
        GetCustomerAccountRequest request = new GetCustomerAccountRequest(126879020L, "441629877739127000000001");
        GetCustomerAccountResult result = (new GetCustomerAccountProvider()).handleRequest(request, null);
    }

    public static void getTransactions() {
        GetAccountTransactionsRequest request = new GetAccountTransactionsRequest(126879020L, "441629877739127000000001");
        GetAccountTransactionsResult result = (new GetAccountTransactionsProvider()).handleRequest(request, null);
    }

    public static void getCustomerInfo() {
        GetCustomerAccountsRequest request = new GetCustomerAccountsRequest();
        request.setCustomerId(126879020L);
        GetCustomerAccountsResult result = (new GetCustomerAccountsProvider()).handleRequest(request, null);
    }

    public static void getCustomers() {
        GetCustomersRequest request = new GetCustomersRequest();
        GetCustomersResult result = (new GetCustomersProvider()).handleRequest(request, null);
    }

    public static void testCredentials() {
        GetCustomerAccountsRequest request = new GetCustomerAccountsRequest(1L);
        GetCustomerAccountsResult result = (new GetCustomerAccountsProvider()).handleRequest(request, null);
    }

    public static void testDynamoDBDao() {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        Transaction transaction = new Transaction();
        transaction.setAccountId("1");
        transaction.setAmount(1.0);
        transaction.setMemo("My new transaction");
        transaction.setSubmittedDateTime(Instant.now().getEpochSecond() - 86400L);
        transaction.setCompletedDateTime(Instant.now().getEpochSecond());
        Account account = new Account();
        account.setAccountNumber(1L);
        account.setRoutingNumber(1L);
        account.setBalance(1.11);
        account.setNickname("My new account");
        account.setType("credit");
        account.setCreditLimit(5000.0);
        account.setCustomerId(1L);
        Customer customer = new Customer();
        customer.setFirstName("Mike");
        customer.setLastName("P");
        customer.setId(1L);
        AccountStub accountStub = new AccountStub();
        accountStub.setAccountId(account.getAccountId());
        accountStub.setBalance(account.getBalance());
        accountStub.setNickname(account.getNickname());
        accountStub.setType(account.getType());
        List<AccountStub> accountStubs = new ArrayList();
        accountStubs.add(accountStub);
        customer.setAccounts(accountStubs);
        CustomerStub customerStub = new CustomerStub();
        customerStub.setFirstName("Billy");
        customerStub.setLastName("P");
        customerStub.setId(2L);
        List<CustomerStub> customerStubs = new ArrayList();
        customerStubs.add(customerStub);
        customer.setContacts(customerStubs);
        ddbMapper.save(customer);
        ddbMapper.save(account);
        ddbMapper.save(transaction);
    }

}
