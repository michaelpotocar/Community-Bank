package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostCreateAccountRequest {
    @With
    @Getter
    @Setter
    private Long customerId;
    @With
    @Getter
    @Setter
    private String type;
    @With
    @Getter
    @Setter
    private String nickname;
    @With
    @Getter
    @Setter
    private Long accountNumber;
    @With
    @Getter
    @Setter
    private Long routingNumber;
    @With
    @Getter
    @Setter
    private Long creditLimit;

}
