package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.TransactionDao;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;
import michaelpotocar.projectkitty.requests.GetAccountTransactionsRequest;
import michaelpotocar.projectkitty.results.GetAccountTransactionsResult;

import java.util.List;

public class GetAccountTransactionsProvider implements RequestHandler<GetAccountTransactionsRequest, GetAccountTransactionsResult> {
    public GetAccountTransactionsResult handleRequest(GetAccountTransactionsRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        String accountId = input.getAccountId();

        List<Transaction> transactions = TransactionDao.get(accountId);

        GetAccountTransactionsResult result = new GetAccountTransactionsResult()
                .withTransactions(transactions)
                .withMessage("Success");

        System.out.println(result);
        return result;
    }
}
