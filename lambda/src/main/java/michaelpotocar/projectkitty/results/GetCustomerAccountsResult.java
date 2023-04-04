package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Account;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetCustomerAccountsResult {
    @With
    @Getter
    @Setter
    private List<Account> accounts;

}
