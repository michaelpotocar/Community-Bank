package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.GetCustomerAccountRequest;
import michaelpotocar.projectkitty.results.GetCustomerAccountResult;

import java.util.stream.Collectors;

public class GetCustomerAccountProvider implements RequestHandler<GetCustomerAccountRequest, GetCustomerAccountResult> {
    public GetCustomerAccountResult handleRequest(GetCustomerAccountRequest input, Context context) {
        System.out.println(input);
        GetCustomerAccountResult result = new GetCustomerAccountResult();

        Customer customer = CustomerDao.get(input.getCustomerId());
        if (customer == null) {
            result.setError("Invalid CustomerId");
            System.out.println(result);
            return result;
        }

        Account account = null;
        try {
            account = customer.getAccounts()
                    .stream()
                    .filter(a -> a.getAccountId().equals(input.getAccountId()))
                    .collect(Collectors.toList())
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            result.setError("Invalid AccountId");
            System.out.println(result);
            return result;
        }

        result.setAccount(account);
        result.setMessage("Success");

        System.out.println(result);
        return result;

    }
}
