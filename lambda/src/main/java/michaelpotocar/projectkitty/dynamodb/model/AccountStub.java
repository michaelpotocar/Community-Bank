package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class AccountStub {
    String accountId;
    String nickname;
    String type;
    Double balance;

    public AccountStub() {
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
}
