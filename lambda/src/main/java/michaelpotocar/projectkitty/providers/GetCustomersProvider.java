package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.List;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.GetCustomersRequest;
import michaelpotocar.projectkitty.results.GetCustomersResult;

public class GetCustomersProvider implements RequestHandler<GetCustomersRequest, GetCustomersResult> {
    public GetCustomersResult handleRequest(GetCustomersRequest input, Context context) {
        System.out.println("Input: " + input.toString());

        List<Customer> customers = CustomerDao.getAll();

        GetCustomersResult result = new GetCustomersResult()
                .withCustomers(customers)
                .withMessage("Success");

        System.out.println("Result: " + result);
        return result;
    }
}
