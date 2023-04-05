package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
@DynamoDBTable(tableName = "Customers")
public class Customer {
    @DynamoDBHashKey(attributeName = "id")
    private Long id;
    @DynamoDBAttribute(attributeName = "accounts")
    private List<Account> accounts;
    @DynamoDBAttribute(attributeName = "contacts")
    private List<CustomerStub> contacts;
    @DynamoDBAttribute(attributeName = "firstName")
    private String firstName;
    @DynamoDBAttribute(attributeName = "lastName")
    private String lastName;

}
