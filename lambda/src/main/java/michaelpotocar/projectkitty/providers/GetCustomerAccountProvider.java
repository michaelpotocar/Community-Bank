package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.AccountDao;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.requests.GetCustomerAccountRequest;
import michaelpotocar.projectkitty.results.GetCustomerAccountResult;

public class GetCustomerAccountProvider implements RequestHandler<GetCustomerAccountRequest, GetCustomerAccountResult> {
    public GetCustomerAccountProvider() {
    }

    public GetCustomerAccountResult handleRequest(GetCustomerAccountRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long accountNumber = input.getAccountNumber();
        Long routingNumber = input.getRoutingNumber();

        Account account = AccountDao.getAccount(accountNumber, routingNumber);

        GetCustomerAccountResult result = new GetCustomerAccountResult(account);

        System.out.println("Result: " + result.toString());
        return result;
    }
}
