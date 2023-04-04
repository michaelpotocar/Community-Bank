package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetCustomerAccountRequest {
    @With
    @Getter
    @Setter
    private Long customerId;
    @With
    @Getter
    @Setter
    private String accountId;

}
