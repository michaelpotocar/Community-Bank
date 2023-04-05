package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.PeerToPeerTransfer;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class GetPendingPeerToPeerTransfersResult {
    private List<PeerToPeerTransfer> p2ps;
    private String message;
    private String error;

}
