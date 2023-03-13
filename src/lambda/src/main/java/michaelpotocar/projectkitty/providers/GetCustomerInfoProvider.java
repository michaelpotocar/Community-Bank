package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.requests.GetCustomerInfoRequest;
import michaelpotocar.projectkitty.results.GetCustomerInfoResult;

public class GetCustomerInfoProvider implements RequestHandler<GetCustomerInfoRequest, GetCustomerInfoResult> {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    @Override
    public GetCustomerInfoResult handleRequest(GetCustomerInfoRequest input, Context context) {
        return null;
    }
}