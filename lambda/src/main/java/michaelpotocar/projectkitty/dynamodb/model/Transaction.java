package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamoDBTable(tableName = "Transactions")
public class Transaction {

    @With
    @Getter
    @Setter
    @DynamoDBHashKey(attributeName = "accountId")
    private String accountId;
    @With
    @Getter
    @Setter
    @DynamoDBRangeKey(attributeName = "submittedDateTime")
    private Long submittedDateTime;
    @With
    @Getter
    @Setter
    @DynamoDBAttribute(attributeName = "amount")
    private Double amount;
    @With
    @Getter
    @Setter
    @DynamoDBAttribute(attributeName = "completedDateTime")
    private Long completedDateTime;
    @With
    @Getter
    @Setter
    @DynamoDBAttribute(attributeName = "memo")
    private String memo;
}
