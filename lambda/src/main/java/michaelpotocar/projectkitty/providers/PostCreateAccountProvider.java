package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.PostCreateAccountRequest;
import michaelpotocar.projectkitty.results.PostCreateAccountResult;

import java.util.Random;
import java.util.stream.Collectors;

public class PostCreateAccountProvider implements RequestHandler<PostCreateAccountRequest, PostCreateAccountResult> {
    public PostCreateAccountResult handleRequest(PostCreateAccountRequest input, Context context) {
        System.out.println("Input: " + input.toString());

        switch (input.getType()) {
            case "checking":
            case "savings":
                break;
            case "credit":
                break;
            case "external":
                break;
            default:
                PostCreateAccountResult result = new PostCreateAccountResult().withError( "Account Type Is Invalid");
                System.out.println("Result: " + result);
                return result;
        }

        Customer customer = CustomerDao.get(input.getCustomerId());
        if(customer == null) {
            PostCreateAccountResult result = new PostCreateAccountResult().withError("Invalid Customer ID");
            System.out.println("Result: " + result);
            return result;
        }

        Account newAccount = new Account();
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
        }


        boolean uniqueIdNumber = !customer.getAccounts().stream().map(a -> a.getAccountId()).collect(Collectors.toSet()).contains(newAccount.getAccountId());
        boolean uniqueNickname = !customer.getAccounts().stream().map(a -> a.getNickname()).collect(Collectors.toSet()).contains(newAccount.getNickname());

        if(!uniqueIdNumber) {
            PostCreateAccountResult result = new PostCreateAccountResult().withError("Account ID Not Unique");
            System.out.println("Result: " + result);
            return result;
        }

        if(!uniqueNickname) {
            PostCreateAccountResult result = new PostCreateAccountResult().withError("Account Nickname Not Unique");
            System.out.println("Result: " + result);
            return result;
        }

        customer.getAccounts().add(newAccount);

        CustomerDao.save(customer);

        PostCreateAccountResult result = new PostCreateAccountResult().withAccount(newAccount).withMessage("Account Created");
        System.out.println("Result: " + result);
        return result;
    }
}
