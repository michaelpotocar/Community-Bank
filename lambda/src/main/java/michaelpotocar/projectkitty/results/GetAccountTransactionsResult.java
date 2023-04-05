package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class GetAccountTransactionsResult {
    private List<Transaction> transactions;
    private String message;
    private String error;

}
