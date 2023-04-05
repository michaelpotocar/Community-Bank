package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.PeerToPeerTransferDao;
import michaelpotocar.projectkitty.dynamodb.model.PeerToPeerTransfer;
import michaelpotocar.projectkitty.requests.GetPendingPeerToPeerTransfersRequest;
import michaelpotocar.projectkitty.results.GetPendingPeerToPeerTransfersResult;

import java.util.List;

public class GetPendingPeerToPeerTransfersProvider implements RequestHandler<GetPendingPeerToPeerTransfersRequest, GetPendingPeerToPeerTransfersResult> {
    public GetPendingPeerToPeerTransfersResult handleRequest(GetPendingPeerToPeerTransfersRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long customerId = input.getCustomerId();

        List<PeerToPeerTransfer> p2ps = PeerToPeerTransferDao.getPending(customerId);

        GetPendingPeerToPeerTransfersResult result = new GetPendingPeerToPeerTransfersResult()
                .withP2ps(p2ps)
                .withMessage("Success");
        System.out.println( result);
        return result;
    }
}
