package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Account;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class GetCustomerAccountResult {
    private Account account;
    private String message;
    private String error;
}
