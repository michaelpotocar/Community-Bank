package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.TransactionDao;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;
import michaelpotocar.projectkitty.requests.GetTransactionsRequest;
import michaelpotocar.projectkitty.results.GetTransactionsResult;

import java.util.List;

public class GetAccountTransactionsProvider implements RequestHandler<GetTransactionsRequest, GetTransactionsResult> {
    public GetAccountTransactionsProvider() {
    }

    public GetTransactionsResult handleRequest(GetTransactionsRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long customerId = input.getCustomerId();
        Long accountNumber = input.getAccountNumber();

        List<Transaction> transactions = TransactionDao.getAccountTransactions(accountNumber);

        GetTransactionsResult result = new GetTransactionsResult(transactions);

        System.out.println("Result: " + result.toString());
        return result;
    }
}
