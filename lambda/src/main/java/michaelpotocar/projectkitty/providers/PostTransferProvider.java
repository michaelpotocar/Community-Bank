package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.dynamodb.model.Transaction;
import michaelpotocar.projectkitty.results.PostTransferResult;
import michaelpotocar.projectkitty.requests.PostTransferRequest;

import java.util.List;
import java.util.stream.Collectors;

public class PostTransferProvider implements RequestHandler<PostTransferRequest, PostTransferResult> {
    public PostTransferProvider() {
    }

    public PostTransferResult handleRequest(PostTransferRequest input, Context context) {
        System.out.println("Input: " + input.toString());

        if (input.isNotValid()) {
            PostTransferResult result = new PostTransferResult("Invalid Request");
            System.out.println("Result: " + result);
            return result;
        }

        Customer customer = CustomerDao.getCustomer(input.getCustomerId());

        if (customer == null) {
            PostTransferResult result = new PostTransferResult("Invalid customerId");
            System.out.println("Result: " + result);
            return result;
        }

        List<Account> customerAccounts = customer.getAccounts();
        List<Account> fundingAccounts = customerAccounts
                .stream()
                .filter(a -> a.getAccountId().equals(input.getFundingAccount()))
                .collect(Collectors.toList());
        if (fundingAccounts.size() == 0) {
            PostTransferResult result = new PostTransferResult("Invalid accountId");
            System.out.println("Result: " + result);
            return result;
        }
        Account fundingAccount = fundingAccounts.get(0);


        Transaction originTransaction = new Transaction();
        Transaction destinationTransaction = new Transaction();

        switch (input.getType()) {
            case "standard":

                break;
            case "credit":

                break;
            case "p2p":

                break;
        }


        DynamoDBMapper mapper = DynamoDbMapperProvider.getDynamoDbMapper();


        PostTransferResult result = new PostTransferResult("Account Created");
        System.out.println("Result: " + result);
        return result;
    }
}
