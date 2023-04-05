package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
@DynamoDBDocument
public class Account {
    String accountId;
    Long accountNumber;
    Long routingNumber;
    Long creditLimit;
    String nickname;
    String type;
    Double balance;

}
