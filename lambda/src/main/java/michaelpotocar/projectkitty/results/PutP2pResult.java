package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class PutP2pResult {
    private Transaction targetTransaction;
    private String message;
    private String error;

}

