package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Account {
    String accountId;
    Long accountNumber;
    Long routingNumber;
    Long creditLimit;
    String nickname;
    String type;
    Double balance;

    public Account() {
    }

    @DynamoDBAttribute(attributeName = "accountId")
    public String getAccountId() {
        return this.accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    @DynamoDBAttribute(attributeName = "balance")
    public Double getBalance() {
        return this.balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @DynamoDBAttribute(attributeName = "accountNumber")
    public Long getAccountNumber() {return accountNumber;}
    public void setAccountNumber(Long accountNumber) {this.accountNumber = accountNumber;}

    @DynamoDBAttribute(attributeName = "routingNumber")
    public Long getRoutingNumber() {return routingNumber;}
    public void setRoutingNumber(Long routingNumber) {this.routingNumber = routingNumber;}

    @DynamoDBAttribute(attributeName = "creditLimit")
    public Long getCreditLimit() {return creditLimit;}
    public void setCreditLimit(Long creditLimit) {this.creditLimit = creditLimit;}

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", accountNumber=" + accountNumber +
                ", routingNumber=" + routingNumber +
                ", creditLimit=" + creditLimit +
                ", nickname='" + nickname + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                '}';
    }
}
