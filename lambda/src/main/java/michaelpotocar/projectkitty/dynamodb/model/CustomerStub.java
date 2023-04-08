package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
@DynamoDBDocument
public class CustomerStub {
    @DynamoDBAttribute(attributeName = "id")
    Long id;
    @DynamoDBAttribute(attributeName = "firstName")
    String firstName;
    @DynamoDBAttribute(attributeName = "lastName")
    String lastName;

    @DynamoDBIgnore
    public String getFullName() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }
}
