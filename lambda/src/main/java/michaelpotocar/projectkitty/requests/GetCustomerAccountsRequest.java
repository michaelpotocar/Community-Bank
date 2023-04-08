package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class GetCustomerAccountsRequest {
    private Long customerId;
}
