package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.GetCustomerRequest;
import michaelpotocar.projectkitty.results.GetCustomerResult;

public class GetCustomerProvider implements RequestHandler<GetCustomerRequest, GetCustomerResult> {
    public GetCustomerResult handleRequest(GetCustomerRequest input, Context context) {
        System.out.println(input);

        Customer customer = CustomerDao.get(input.getCustomerId());

        GetCustomerResult result = new GetCustomerResult()
                .withCustomer(customer)
                .withMessage("Success");

        System.out.println(result);
        return result;
    }
}
