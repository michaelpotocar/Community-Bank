//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package michaelpotocar.projectkitty;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.AccountStub;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.dynamodb.model.CustomerStub;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;
import michaelpotocar.projectkitty.providers.GetCustomerInfoProvider;
import michaelpotocar.projectkitty.providers.GetCustomersProvider;
import michaelpotocar.projectkitty.requests.GetCustomerInfoRequest;
import michaelpotocar.projectkitty.requests.GetCustomersRequest;
import michaelpotocar.projectkitty.results.GetCustomerInfoResult;
import michaelpotocar.projectkitty.results.GetCustomersResult;

public class Main {
    private static final DefaultAWSCredentialsProviderChain credentialsProvider = new DefaultAWSCredentialsProviderChain();
    private static final ProfileCredentialsProvider localCredentialsProvider = new ProfileCredentialsProvider("bt");
    private static final AmazonDynamoDB ddbClient;
    private static final DynamoDBMapper ddbMapper;

    public Main() {
    }

    public static void main(String[] args) {
        getCustomerInfo();
    }

    public static void getCustomerInfo() {
        GetCustomerInfoRequest request = new GetCustomerInfoRequest();
        request.setId(126879020l);
        GetCustomerInfoResult result = (new GetCustomerInfoProvider()).handleRequest(request, (Context)null);
    }
    public static void getCustomers() {
        GetCustomersRequest request = new GetCustomersRequest();
        GetCustomersResult result = (new GetCustomersProvider()).handleRequest(request, (Context)null);
    }

    public static void testCredentials() {
        GetCustomerInfoRequest request = new GetCustomerInfoRequest(1L);
        GetCustomerInfoResult result = (new GetCustomerInfoProvider()).handleRequest(request, (Context)null);
    }

    public static void testDynamoDBDao() {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(1L);
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
        accountStub.setAccountNumber(account.getAccountNumber());
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

    static {
        if (System.getenv("AWS_EXECUTION_ENV") != null) {
            ddbClient = (AmazonDynamoDB)((AmazonDynamoDBClientBuilder)((AmazonDynamoDBClientBuilder)AmazonDynamoDBClientBuilder.standard().withCredentials(credentialsProvider)).withRegion(Regions.US_WEST_2)).build();
        } else {
            ddbClient = (AmazonDynamoDB)((AmazonDynamoDBClientBuilder)((AmazonDynamoDBClientBuilder)AmazonDynamoDBClientBuilder.standard().withCredentials(localCredentialsProvider)).withRegion(Regions.US_WEST_2)).build();
        }

        ddbMapper = new DynamoDBMapper(ddbClient);
    }
}
