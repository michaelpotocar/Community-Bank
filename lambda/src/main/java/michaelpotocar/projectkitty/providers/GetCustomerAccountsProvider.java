package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.AccountDao;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.requests.GetCustomerAccountsRequest;
import michaelpotocar.projectkitty.results.GetCustomerAccountsResult;

import java.util.List;

public class GetCustomerAccountsProvider implements RequestHandler<GetCustomerAccountsRequest, GetCustomerAccountsResult> {
    public GetCustomerAccountsProvider() {
    }

    public GetCustomerAccountsResult handleRequest(GetCustomerAccountsRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long customerID = input.getCustomerId();

        List<Account> accounts  = AccountDao.getAccounts(customerID);

        GetCustomerAccountsResult result = new GetCustomerAccountsResult(accounts);

        System.out.println("Result: " + result.toString());
        return result;
    }
}
