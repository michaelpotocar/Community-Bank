package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import java.util.List;

import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.model.Customer;

public class CustomerDao {

    static DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();

    public static Customer get(Long id) {
        Customer customer = ddbMapper.load(Customer.class, id);
        return customer;
    }

    public static List<Customer> getAll() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Customer> customers = ddbMapper.scan(Customer.class, scanExpression);
        return customers;
    }

    public static void save(Customer customer) {
        ddbMapper.save(customer);
    }
}
