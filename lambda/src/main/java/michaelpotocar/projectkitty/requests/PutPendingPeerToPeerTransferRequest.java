package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class PutPendingPeerToPeerTransferRequest {
    private Long customerId;
    private String targetAccountId;
    private Long transferId;
}
