package michaelpotocar.projectkitty;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;

public class Main {
    public static void main(String[] args) {

        AmazonDynamoDB ddbClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider("bt"))
                .withRegion(Regions.US_WEST_2) // Replace with the region where your table is located
                .build();

        DynamoDBMapper ddbMapper = new DynamoDBMapper(ddbClient);

        Transaction t1 = ddbMapper.load(Transaction.class, 1426773852438830L, 1641370204L);

        Account a1 = ddbMapper.load(Account.class, 1426773852438830L, 0l);

        Customer c1 = ddbMapper.load(Customer.class, 811259776L);

        c1.getAccounts().get(0).setType(null);

        ddbMapper.save(c1);

        System.out.println("");

    }
}
