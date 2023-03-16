package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ProjectKittyAccounts")
public class Account {
    private Long accountNumber;
    private Long routingNumber;
    private Double balance;
    private Long customerId;
    private String nickname;
    private String type;

    @DynamoDBHashKey(attributeName = "accountNumber")
    public Long getAccountNumber() {return accountNumber;}
    public void setAccountNumber(Long accountNumber) {this.accountNumber = accountNumber;}

    @DynamoDBRangeKey(attributeName = "routingNumber")
    public Long getRoutingNumber() {return routingNumber;}
    public void setRoutingNumber(Long routingNumber) {this.routingNumber = routingNumber;}

    @DynamoDBAttribute(attributeName = "balance")
    public Double getBalance() {return balance;}
    public void setBalance(Double balance) {this.balance = balance;}

    @DynamoDBAttribute(attributeName = "customerId")
    public Long getCustomerId() {return customerId;}
    public void setCustomerId(Long customerId) {this.customerId = customerId;}

    @DynamoDBAttribute(attributeName = "nickname")
    public String getNickname() {return nickname;}
    public void setNickname(String nickname) {this.nickname = nickname;}

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

}







