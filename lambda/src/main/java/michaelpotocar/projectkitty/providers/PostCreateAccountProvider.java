package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.dao.AccountDao;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.requests.PostCreateAccountRequest;
import michaelpotocar.projectkitty.results.PostCreateAccountResult;

import java.util.Random;

public class PostCreateAccountProvider implements RequestHandler<PostCreateAccountRequest, PostCreateAccountResult> {
    public PostCreateAccountProvider() {
    }

    public PostCreateAccountResult handleRequest(PostCreateAccountRequest input, Context context) {
        System.out.println("Input: " + input.toString());

        DynamoDBMapper mapper = DynamoDbMapperProvider.getDynamoDbMapper();

        Account newAccount = new Account();
        newAccount.setCustomerId(input.getCustomerId());
        newAccount.setType(input.getType());
        newAccount.setNickname(input.getNickname());

        Random random;
        long lowerBound;
        long upperBound;
        Long newAccountNumber;

        switch (input.getType()) {
            case "checking":
            case "savings":
                random = new Random();
                lowerBound = 100_000_000_000L;
                upperBound = 999_999_999_999L;
                newAccountNumber = lowerBound + (long) (random.nextDouble() * (upperBound - lowerBound));

                newAccount.setAccountId(newAccountNumber + "127000000001");
                newAccount.setAccountNumber(newAccountNumber);
                newAccount.setRoutingNumber(input.getRoutingNumber());
                newAccount.setBalance(0.0);
                break;
            case "credit":
                random = new Random();
                lowerBound = 1_000_000_000_000_000L;
                upperBound = 9_999_999_999_999_999L;
                newAccountNumber = lowerBound + (long) (random.nextDouble() * (upperBound - lowerBound));

                newAccount.setAccountId(newAccountNumber.toString());
                newAccount.setAccountNumber(newAccountNumber);
                newAccount.setCreditLimit(input.getCreditLimit());
                newAccount.setBalance(0.0);
                break;
            case "external":
                newAccount.setAccountId(input.getAccountNumber().toString() + input.getRoutingNumber().toString());
                newAccount.setAccountNumber(input.getAccountNumber());
                newAccount.setRoutingNumber(input.getRoutingNumber());
                break;
            default:
                PostCreateAccountResult result = new PostCreateAccountResult(null, "Error: Account Type Is Invalid");
                System.out.println("Result: " + result);
                return result;
        }

        try {
            AccountDao.saveAccount(newAccount);
        } catch (Exception e) {
            PostCreateAccountResult result = new PostCreateAccountResult(null, e.toString());
            System.out.println("Result: " + result);
            return result;
        }

        PostCreateAccountResult result = new PostCreateAccountResult(newAccount, "Account Created");
        System.out.println("Result: " + result);
        return result;
    }
}
