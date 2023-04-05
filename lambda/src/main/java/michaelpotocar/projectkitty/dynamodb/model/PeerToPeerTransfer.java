
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
@DynamoDBTable(tableName = "PeerToPeerTransfers")
public class PeerToPeerTransfer {
    @DynamoDBHashKey(attributeName = "targetCustomerId")
    private Long targetCustomerId;
    @DynamoDBRangeKey(attributeName = "submittedDateTime")
    private Long submittedDateTime;
    @DynamoDBAttribute(attributeName = "fundingCustomerId")
    private Long fundingCustomerId;
    @DynamoDBAttribute(attributeName = "amount")
    private Double amount;
    @DynamoDBAttribute(attributeName = "memo")
    private String memo;
    @DynamoDBAttribute(attributeName = "completedDateTime")
    private Long completedDateTime;
}
