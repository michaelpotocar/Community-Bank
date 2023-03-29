package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.TransactionDao;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;
import michaelpotocar.projectkitty.requests.GetTransactionsRequest;
import michaelpotocar.projectkitty.results.GetTransactionsResult;

import java.util.List;

public class GetTransactionsProvider implements RequestHandler<GetTransactionsRequest, GetTransactionsResult> {
    public GetTransactionsProvider() {
    }

    public GetTransactionsResult handleRequest(GetTransactionsRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long customerId = input.getCustomerId();
        Long accountId = input.getAccountId();

        List<Transaction> transactions = TransactionDao.getAccountTransactions(accountId);

        GetTransactionsResult result = new GetTransactionsResult(transactions);

        System.out.println("Result: " + result.toString());
        return result;
    }
}
