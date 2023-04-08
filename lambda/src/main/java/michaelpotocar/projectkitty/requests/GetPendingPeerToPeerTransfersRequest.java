package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class GetPendingPeerToPeerTransfersRequest {
    private Long customerId;
}
