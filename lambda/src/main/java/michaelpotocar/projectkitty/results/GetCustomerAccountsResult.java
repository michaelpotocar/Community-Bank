package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Account;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class GetCustomerAccountsResult {
    private List<Account> accounts;
    private String message;
    private String error;
}
