package michaelpotocar.projectkitty;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import michaelpotocar.projectkitty.dynamodb.dao.TransactionDao;
import michaelpotocar.projectkitty.dynamodb.model.*;
import michaelpotocar.projectkitty.providers.GetCustomerInfoProvider;
import michaelpotocar.projectkitty.requests.GetCustomerInfoRequest;
import michaelpotocar.projectkitty.results.GetCustomerInfoResult;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final DefaultAWSCredentialsProviderChain credentialsProvider;
    private static final ProfileCredentialsProvider localCredentialsProvider;
    private static final AmazonDynamoDB ddbClient;
    private static final DynamoDBMapper ddbMapper;

    static {
        credentialsProvider = new DefaultAWSCredentialsProviderChain();
        localCredentialsProvider = new ProfileCredentialsProvider("bt");

        if (System.getenv("AWS_EXECUTION_ENV") != null) {
            ddbClient = AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(credentialsProvider)
                    .withRegion(Regions.US_WEST_2)
                    .build();
        } else {
            ddbClient = AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(localCredentialsProvider)
                    .withRegion(Regions.US_WEST_2)
                    .build();
        }
        ddbMapper = new DynamoDBMapper(ddbClient);
    }

    public static void main(String[] args) {

        GetCustomerInfoRequest request = new GetCustomerInfoRequest(1l);

        GetCustomerInfoResult result = new GetCustomerInfoProvider().handleRequest(request, null);

        System.out.println();
    }

    public static void testDynamoDBDao() {

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(1l);
        transaction.setAmount(1d);
        transaction.setMemo("My new transaction");
        transaction.setSubmittedDateTime(java.time.Instant.now().getEpochSecond() - 60 * 60 * 24);
        transaction.setCompletedDateTime(java.time.Instant.now().getEpochSecond());

        Account account = new Account();
        account.setAccountNumber(1L);
        account.setRoutingNumber(1L);
        account.setBalance(1.11d);
        account.setNickname("My new account");
        account.setType("credit");
        account.setCreditLimit(5000d);
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
        List<AccountStub> accountStubs = new ArrayList<>();
        accountStubs.add(accountStub);
        customer.setAccounts(accountStubs);

        ContactStub contactStub = new ContactStub();
        contactStub.setFirstName("Billy");
        contactStub.setLastName("P");
        contactStub.setId(2L);
        List<ContactStub> contactStubs = new ArrayList<>();
        contactStubs.add(contactStub);
        customer.setContacts(contactStubs);

        ddbMapper.save(customer);
        ddbMapper.save(account);
        ddbMapper.save(transaction);
    }
    }
