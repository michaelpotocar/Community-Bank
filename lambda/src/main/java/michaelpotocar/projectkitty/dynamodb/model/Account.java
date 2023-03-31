package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(
        tableName = "Accounts"
)
public class Account {
    private Long customerId;
    private String accountId;
    private Long accountNumber;
    private Long routingNumber;
    private Double balance;
    private String nickname;
    private String type;
    private Double creditLimit;

    public Account() {
    }

    public Account(Long customerId, String accountId) {
        this.setCustomerId(customerId);
        this.setAccountId(accountId);
    }

    @DynamoDBHashKey(attributeName = "customerId")
    public Long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(Long customerId) {this.customerId = customerId;}

    @DynamoDBRangeKey(attributeName = "accountId")
    public String getAccountId() {
        return this.accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @DynamoDBAttribute(attributeName = "accountNumber")
    public Long getAccountNumber() {return accountNumber;}
    public void setAccountNumber(Long accountNumber) {this.accountNumber = accountNumber;}

    @DynamoDBAttribute(attributeName = "routingNumber")
    public Long getRoutingNumber() {
        return this.routingNumber;
    }
    public void setRoutingNumber(Long routingNumber) {
        this.routingNumber = routingNumber;
    }

    @DynamoDBAttribute(attributeName = "balance")
    public Double getBalance() {
        return this.balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @DynamoDBAttribute(attributeName = "nickname")
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "creditLimit")
    public Double getCreditLimit() {
        return this.creditLimit;
    }
    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }
}