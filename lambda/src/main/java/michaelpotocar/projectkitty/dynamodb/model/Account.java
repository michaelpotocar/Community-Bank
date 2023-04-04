package michaelpotocar.projectkitty.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamoDBDocument
public class Account {
    @With
    @Getter
    @Setter
    String accountId;
    @With
    @Getter
    @Setter
    Long accountNumber;
    @With
    @Getter
    @Setter
    Long routingNumber;
    @With
    @Getter
    @Setter
    Long creditLimit;
    @With
    @Getter
    @Setter
    String nickname;
    @With
    @Getter
    @Setter
    String type;
    @With
    @Getter
    @Setter
    Double balance;
}
