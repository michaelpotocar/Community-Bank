package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Account;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetCustomerAccountResult {
    @With
    @Getter
    @Setter
    private Account account;

}
