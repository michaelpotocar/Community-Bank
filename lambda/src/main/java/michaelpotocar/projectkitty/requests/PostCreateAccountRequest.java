package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class PostCreateAccountRequest {
    private Long customerId;
    private String type;
    private String nickname;
    private Long accountNumber;
    private Long routingNumber;
    private Long creditLimit;
}
