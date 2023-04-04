package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetAccountTransactionsResult {
    @With
    @Getter
    @Setter
    private List<Transaction> transactions;
}
