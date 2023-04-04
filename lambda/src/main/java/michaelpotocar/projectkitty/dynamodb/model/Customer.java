package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamoDBTable(tableName = "Customers")
public class Customer {
    @With
    @Getter
    @Setter
    @DynamoDBHashKey(attributeName = "id")
    private Long id;
    @With
    @Getter
    @Setter
    @DynamoDBAttribute(attributeName = "accounts")
    private List<Account> accounts;
    @With
    @Getter
    @Setter
    @DynamoDBAttribute(attributeName = "contacts")
    private List<CustomerStub> contacts;
    @With
    @Getter
    @Setter
    @DynamoDBAttribute(attributeName = "firstName")
    private String firstName;
    @With
    @Getter
    @Setter
    @DynamoDBAttribute(attributeName = "lastName")
    private String lastName;

}
