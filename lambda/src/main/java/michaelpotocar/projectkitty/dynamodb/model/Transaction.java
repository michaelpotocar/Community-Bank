package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Transactions")
public class Transaction {
    private String accountId;
    private Long submittedDateTime;
    private Double amount;
    private Long completedDateTime;
    private String memo;

    public Transaction() {}

    @DynamoDBHashKey(attributeName = "accountId")
    public String getAccountId() {
        return this.accountId;
    }
    public void setAccountId(String accountId) {this.accountId = accountId;}

    @DynamoDBRangeKey(attributeName = "submittedDateTime")
    public Long getSubmittedDateTime() {
        return this.submittedDateTime;
    }
    public void setSubmittedDateTime(Long submittedDateTime) {
        this.submittedDateTime = submittedDateTime;
    }

    @DynamoDBAttribute(attributeName = "amount")
    public Double getAmount() {
        return this.amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @DynamoDBAttribute(attributeName = "completedDateTime"
    )
    public Long getCompletedDateTime() {
        return this.completedDateTime;
    }
    public void setCompletedDateTime(Long completedDateTime) {
        this.completedDateTime = completedDateTime;
    }

    @DynamoDBAttribute(attributeName = "memo")
    public String getMemo() {
        return this.memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
}
