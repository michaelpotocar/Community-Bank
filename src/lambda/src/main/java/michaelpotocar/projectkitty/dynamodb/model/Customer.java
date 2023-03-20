package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "ProjectKittyCustomers")
public class Customer {
    private Long id;
    private List<AccountStub> accounts;
    private List<ContactStub> contacts;
    private String firstName;
    private String lastName;

    @DynamoDBHashKey(attributeName = "id")
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @DynamoDBAttribute(attributeName = "accounts")
    public List<AccountStub> getAccounts() {return accounts;}
    public void setAccounts(List<AccountStub> accounts) {this.accounts = accounts;}

    @DynamoDBAttribute(attributeName = "contacts")
    public List<ContactStub> getContacts() {return contacts;}
    public void setContacts(List<ContactStub> contacts) {this.contacts = contacts;}

    @DynamoDBAttribute(attributeName = "getFirstName")
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    @DynamoDBAttribute(attributeName = "getLastName")
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

}
