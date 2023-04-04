package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostTransferRequest {
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
    private String fundingAccount;
    @With
    @Getter
    @Setter
    private String targetAccount;
    @With
    @Getter
    @Setter
    private Long contactId;
    @With
    @Getter
    @Setter
    private Double amount;
    @With
    @Getter
    @Setter
    private String memo;

}
