package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class PostTransferResult {
    private Transaction fundingTransaction;
    private Transaction targetTransaction;
    private String message;
    private String error;

}

