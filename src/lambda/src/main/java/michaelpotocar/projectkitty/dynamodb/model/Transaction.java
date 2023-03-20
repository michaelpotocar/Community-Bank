package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ProjectKittyTransactions")
public class Transaction {
    private Long accountNumber;
    private Long submittedDateTime;
    private Double amount;
    private Long completedDateTime;
    private String memo;

    @DynamoDBHashKey(attributeName = "accountNumber")
    public Long getAccountNumber() {return accountNumber;}
    public void setAccountNumber(Long accountNumber) {this.accountNumber = accountNumber;}

    @DynamoDBRangeKey(attributeName = "submittedDateTime")
    public Long getSubmittedDateTime() {return submittedDateTime;}
    public void setSubmittedDateTime(Long submittedDateTime) {this.submittedDateTime = submittedDateTime;}

    @DynamoDBAttribute(attributeName = "amount")
    public Double getAmount() {return amount;}
    public void setAmount(Double amount) {this.amount = amount;}

    @DynamoDBAttribute(attributeName = "completedDateTime")
    public Long getCompletedDateTime() {return completedDateTime;}
    public void setCompletedDateTime(Long completedDateTime) {this.completedDateTime = completedDateTime;}

    @DynamoDBAttribute(attributeName = "memo")
    public String getMemo() {return memo;}
    public void setMemo(String memo) {this.memo = memo;}

}
