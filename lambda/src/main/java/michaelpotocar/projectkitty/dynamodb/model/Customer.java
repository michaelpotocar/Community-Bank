package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.util.List;

@DynamoDBTable(
        tableName = "Customers"
)
public class Customer {
    private Long id;
    private List<AccountStub> accounts;
    private List<CustomerStub> contacts;
    private String firstName;
    private String lastName;

    public Customer() {
    }

    @DynamoDBHashKey(attributeName = "id")
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "accounts")
    public List<AccountStub> getAccounts() {
        return this.accounts;
    }
    public void setAccounts(List<AccountStub> accounts) {
        this.accounts = accounts;
    }

    @DynamoDBAttribute(attributeName = "contacts")
    public List<CustomerStub> getContacts() {
        return this.contacts;
    }
    public void setContacts(List<CustomerStub> contacts) {
        this.contacts = contacts;
    }

    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDBAttribute(attributeName = "lastName")
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
