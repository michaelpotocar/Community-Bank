package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class PutPendingPeerToPeerTransferResult {
    private Transaction targetTransaction;
    private String message;
    private String error;
}

