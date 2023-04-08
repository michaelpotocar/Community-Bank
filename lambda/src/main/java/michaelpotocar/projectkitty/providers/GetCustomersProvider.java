package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.GetCustomersRequest;
import michaelpotocar.projectkitty.results.GetCustomersResult;

import java.util.List;

public class GetCustomersProvider implements RequestHandler<GetCustomersRequest, GetCustomersResult> {
    public GetCustomersResult handleRequest(GetCustomersRequest input, Context context) {
        System.out.println(input);

        List<Customer> customers = CustomerDao.getAll();

        GetCustomersResult result = new GetCustomersResult()
                .withCustomers(customers)
                .withMessage("Success");

        System.out.println(result);
        return result;
    }
}
