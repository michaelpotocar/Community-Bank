package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamoDBDocument
public class CustomerStub {
    @With
    @Getter
    @Setter
    @DynamoDBAttribute(attributeName = "id")
    Long id;
    @With
    @Getter
    @Setter
    @DynamoDBAttribute(attributeName = "firstName")
    String firstName;
    @With
    @Getter
    @Setter
    @DynamoDBAttribute(attributeName = "lastName")
    String lastName;
}
