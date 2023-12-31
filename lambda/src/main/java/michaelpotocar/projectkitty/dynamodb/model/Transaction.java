package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
@DynamoDBTable(tableName = "Transactions")
public class Transaction {
    @DynamoDBHashKey(attributeName = "accountId")
    private String accountId;
    @DynamoDBRangeKey(attributeName = "submittedDateTime")
    private Long submittedDateTime;
    @DynamoDBAttribute(attributeName = "amount")
    private Double amount;
    @DynamoDBAttribute(attributeName = "completedDateTime")
    private Long completedDateTime;
    @DynamoDBAttribute(attributeName = "memo")
    private String memo;

}
