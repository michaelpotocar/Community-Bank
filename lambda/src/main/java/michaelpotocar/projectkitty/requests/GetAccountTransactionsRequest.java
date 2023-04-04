package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetAccountTransactionsRequest {
    @With
    @Getter
    @Setter
    private Long customerId;
    @With
    @Getter
    @Setter
    private String accountId;

}
