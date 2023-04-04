package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetCustomerRequest {

    @With
    @Getter
    @Setter
    private Long customerId;

}
