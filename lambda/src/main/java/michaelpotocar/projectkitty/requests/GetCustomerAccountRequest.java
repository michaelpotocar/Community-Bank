package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class GetCustomerAccountRequest {
    private Long customerId;
    private String accountId;
}
