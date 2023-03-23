//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import java.util.List;
import michaelpotocar.projectkitty.dynamodb.model.Customer;

public class CustomerDao {
    private static final DefaultAWSCredentialsProviderChain credentialsProvider = new DefaultAWSCredentialsProviderChain();
    private static final ProfileCredentialsProvider localCredentialsProvider = new ProfileCredentialsProvider("bt");
    private static final AmazonDynamoDB ddbClient;
    private static final DynamoDBMapper ddbMapper;

    public CustomerDao() {
    }

    public static Customer getCustomer(Long id) {
        Customer customer = (Customer)ddbMapper.load(Customer.class, id);
        return customer;
    }

    public static List<Customer> getAllCustomers() {
        System.out.println("getAllCustomers1");
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        System.out.println("getAllCustomers2");
        List<Customer> customers = ddbMapper.scan(Customer.class, scanExpression);
        System.out.println("getAllCustomers3");
        return customers;
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
