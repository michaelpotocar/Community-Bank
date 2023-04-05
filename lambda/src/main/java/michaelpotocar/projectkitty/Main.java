
package michaelpotocar.projectkitty;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.dynamodb.model.CustomerStub;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;
import michaelpotocar.projectkitty.providers.*;
import michaelpotocar.projectkitty.requests.*;
import michaelpotocar.projectkitty.results.*;

public class Main {

    public static void main(String[] args) {
        getPendingPeerToPeerTransfer();
    }


    public static void getPendingPeerToPeerTransfer() {
        GetPendingPeerToPeerTransfersRequest request = new GetPendingPeerToPeerTransfersRequest()
                .withCustomerId(589631133L);

        GetPendingPeerToPeerTransfersResult result = (new GetPendingPeerToPeerTransfersProvider()).handleRequest(request, null);
    }

    public static void postStandardTransfer() {
        PostTransferRequest request = new PostTransferRequest()
                .withCustomerId(589631133L)
                .withFundingAccountId("590761751601127000000001")
                .withTargetAccountId("745844482971127000000001")
                .withAmount(1.0)
                .withType("standard")
                .withMemo("testing");

        PostTransferResult result = (new PostTransferProvider()).handleRequest(request, null);
    }

    public static void postCreditTransfer() {
        PostTransferRequest request = new PostTransferRequest()
                .withCustomerId(637818676L)
                .withFundingAccountId("973891075646127000000001")
                .withTargetAccountId("8755129424701580")
                .withAmount(1.0)
                .withType("credit")
                .withMemo("testing");

        PostTransferResult result = (new PostTransferProvider()).handleRequest(request, null);
    }

    public static void postP2PTransfer() {
        PostTransferRequest request = new PostTransferRequest()
                .withCustomerId(637818676L)
                .withFundingAccountId("810077691050127000000001")
                .withTargetContactId(589631133L)
                .withAmount(1.0)
                .withType("p2p")
                .withMemo("testing");

        PostTransferResult result = (new PostTransferProvider()).handleRequest(request, null);
    }

    public static void postCreateAccount() {
        PostCreateAccountRequest request = new PostCreateAccountRequest(923739977L, "credit", "13", 2L, 3L, 4L);
        PostCreateAccountResult result = (new PostCreateAccountProvider()).handleRequest(request, null);
    }

    public static void getCustomerAccounts() {
        GetCustomerAccountsRequest request = new GetCustomerAccountsRequest(126879020L);
        GetCustomerAccountsResult result = (new GetCustomerAccountsProvider()).handleRequest(request, null);
    }

    public static void getCustomerAccount() {
        GetCustomerAccountRequest request = new GetCustomerAccountRequest(637818676L, "810077691050127000000001");
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
        account.setCreditLimit(5000L);
        Customer customer = new Customer();
        customer.setFirstName("Mike");
        customer.setLastName("P");
        customer.setId(1L);
        Account accountStub = new Account();
        accountStub.setAccountId(account.getAccountId());
        accountStub.setBalance(account.getBalance());
        accountStub.setNickname(account.getNickname());
        accountStub.setType(account.getType());
        List<Account> accounts = new ArrayList();
        accounts.add(accountStub);
        customer.setAccounts(accounts);
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
