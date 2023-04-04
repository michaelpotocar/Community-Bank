package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetCustomerAccountsRequest {
    @With
    @Getter
    @Setter
    private Long customerId;

}
