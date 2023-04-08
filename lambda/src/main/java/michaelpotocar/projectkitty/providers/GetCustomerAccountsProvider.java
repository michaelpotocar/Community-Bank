package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.GetCustomerAccountsRequest;
import michaelpotocar.projectkitty.results.GetCustomerAccountsResult;

import java.util.List;

public class GetCustomerAccountsProvider implements RequestHandler<GetCustomerAccountsRequest, GetCustomerAccountsResult> {
    public GetCustomerAccountsResult handleRequest(GetCustomerAccountsRequest input, Context context) {
        System.out.println(input);

        Customer customer = CustomerDao.get(input.getCustomerId());
        if (customer == null) {
            GetCustomerAccountsResult result = new GetCustomerAccountsResult()
                    .withError("Invalid CustomerId");
            System.out.println(result);
            return result;
        }

        List<Account> accounts = customer.getAccounts();
        GetCustomerAccountsResult result = new GetCustomerAccountsResult()
                .withAccounts(accounts)
                .withMessage("Success");

        System.out.println(result);
        return result;
    }
}
