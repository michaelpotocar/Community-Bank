package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import java.util.List;

import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.model.Customer;

public class CustomerDao {

    public CustomerDao() {
    }

    public static Customer getCustomer(Long id) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        Customer customer = ddbMapper.load(Customer.class, id);
        return customer;
    }

    public static List<Customer> getAllCustomers() {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Customer> customers = ddbMapper.scan(Customer.class, scanExpression);
        return customers;
    }
}
