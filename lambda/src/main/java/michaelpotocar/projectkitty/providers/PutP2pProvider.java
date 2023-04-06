package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.requests.PutP2pRequest;
import michaelpotocar.projectkitty.results.PutP2pResult;

public class PutP2pProvider implements RequestHandler<PutP2pRequest, PutP2pResult> {
    public PutP2pResult handleRequest(PutP2pRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        PutP2pResult result = new PutP2pResult();



        System.out.println(result);
        return result;
    }
}
