package michaelpotocar.projectkitty.dynamodb;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDbMapperProvider {
    private static final DefaultAWSCredentialsProviderChain credentialsProvider = new DefaultAWSCredentialsProviderChain();
    private static final ProfileCredentialsProvider localCredentialsProvider = new ProfileCredentialsProvider("bt");
    private static final AmazonDynamoDB ddbClient;
    private static final DynamoDBMapper ddbMapper;

    static {
        if (System.getenv("AWS_EXECUTION_ENV") != null) {
            ddbClient = AmazonDynamoDBClientBuilder
                    .standard()
                    .withCredentials(credentialsProvider)
                    .withRegion(Regions.US_WEST_2)
                    .build();
        } else {
            ddbClient = AmazonDynamoDBClientBuilder
                    .standard()
                    .withCredentials(localCredentialsProvider)
                    .withRegion(Regions.US_WEST_2)
                    .build();
        }

        ddbMapper = new DynamoDBMapper(ddbClient);
    }

    public static DynamoDBMapper getDynamoDbMapper() {
        return ddbMapper;
    }

}
