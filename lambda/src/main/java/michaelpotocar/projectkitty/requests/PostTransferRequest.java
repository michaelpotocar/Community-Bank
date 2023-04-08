package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class PostTransferRequest {
    private Long customerId;
    private String type;
    private String fundingAccountId;
    private String targetAccountId;
    private Long targetContactId;
    private Double amount;
    private String memo;
}
