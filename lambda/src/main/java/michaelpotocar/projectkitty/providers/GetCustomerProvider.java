package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.GetCustomerRequest;
import michaelpotocar.projectkitty.results.GetCustomerResult;

import java.util.ArrayList;
import java.util.List;

public class GetCustomerProvider implements RequestHandler<GetCustomerRequest, GetCustomerResult> {
    public GetCustomerProvider() {
    }

    public GetCustomerResult handleRequest(GetCustomerRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long customerId = input.getCustomerId();

        Customer customer = CustomerDao.getCustomer(customerId);

        GetCustomerResult result = new GetCustomerResult(customer);

        System.out.println("Result: " + result.toString());
        return result;
    }
}
