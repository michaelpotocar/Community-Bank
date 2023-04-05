package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.GetCustomerRequest;
import michaelpotocar.projectkitty.results.GetCustomerResult;

public class GetCustomerProvider implements RequestHandler<GetCustomerRequest, GetCustomerResult> {
    public GetCustomerResult handleRequest(GetCustomerRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long customerId = input.getCustomerId();

        Customer customer = CustomerDao.get(customerId);

        GetCustomerResult result = new GetCustomerResult()
                .withCustomer(customer)
                .withMessage("Success");

        System.out.println("Result: " + result);
        return result;
    }
}
