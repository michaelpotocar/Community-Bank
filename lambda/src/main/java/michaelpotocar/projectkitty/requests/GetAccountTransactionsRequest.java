package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class GetAccountTransactionsRequest {
    private Long customerId;
    private String accountId;
}
