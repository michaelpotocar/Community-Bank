package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument

public class ContactStub {
    Long id;
    String firstName;
    String lastName;

    @DynamoDBAttribute(attributeName = "id")
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    @DynamoDBAttribute(attributeName = "lastName")
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

}
