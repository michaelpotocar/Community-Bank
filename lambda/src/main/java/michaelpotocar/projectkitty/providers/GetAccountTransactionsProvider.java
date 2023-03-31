package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.TransactionDao;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;
import michaelpotocar.projectkitty.requests.GetAccountTransactionsRequest;
import michaelpotocar.projectkitty.results.GetAccountTransactionsResult;

import java.util.List;

public class GetAccountTransactionsProvider implements RequestHandler<GetAccountTransactionsRequest, GetAccountTransactionsResult> {
    public GetAccountTransactionsProvider() {
    }

    public GetAccountTransactionsResult handleRequest(GetAccountTransactionsRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long customerId = input.getCustomerId();
        String accountId = input.getAccountId();

        List<Transaction> transactions = TransactionDao.getAccountTransactions(accountId);

        GetAccountTransactionsResult result = new GetAccountTransactionsResult(transactions);

        System.out.println("Result: " + result.toString());
        return result;
    }
}
