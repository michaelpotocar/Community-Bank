package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class AccountStub {
    Long accountNumber;
    String nickname;
    String type;
    Double balance;

    @DynamoDBAttribute(attributeName = "accountNumber")
    public Long getAccountNumber() {return accountNumber;}
    public void setAccountNumber(Long accountNumber) {this.accountNumber = accountNumber;}

    @DynamoDBAttribute(attributeName = "nickname")
    public String getNickname() {return nickname;}
    public void setNickname(String nickname) {this.nickname = nickname;}

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    @DynamoDBAttribute(attributeName = "balance")
    public Double getBalance() {return balance;}
    public void setBalance(Double balance) {this.balance = balance;}
}
