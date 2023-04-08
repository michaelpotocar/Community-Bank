package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.PostCreateAccountRequest;
import michaelpotocar.projectkitty.results.PostCreateAccountResult;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PostCreateAccountProvider implements RequestHandler<PostCreateAccountRequest, PostCreateAccountResult> {
    public PostCreateAccountResult handleRequest(PostCreateAccountRequest input, Context context) {
        System.out.println(input);

        PostCreateAccountResult result = new PostCreateAccountResult();

        List<String> accountTypes = Arrays.asList("checking", "savings", "credit", "external");
        if (!accountTypes.contains(input.getType())) {
            result
                    .setError("Invalid Account Type");
            System.out.println(result);
            return result;
        }

        Customer customer = CustomerDao.get(input.getCustomerId());
        if (customer == null) {
            result
                    .setError("Invalid CustomerId");
            System.out.println(result);
            return result;
        }

        Account newAccount = new Account()
                .withType(input.getType())
                .withNickname(input.getNickname());

        long standardLowerBound = 100_000_000_000L;
        long standardUpperBound = 999_999_999_999L;
        long standardRoutingNumber = 127_000_000_001L;
        long creditLowerBound = 1_000_000_000_000_000L;
        long creditUpperBound = 9_999_999_999_999_999L;

        Random random = new Random();
        long newStandardAccountNumber = standardLowerBound + (long) (random.nextDouble() * (standardUpperBound - standardLowerBound));
        long newCreditAccountNumber = creditLowerBound + (long) (random.nextDouble() * (creditUpperBound - creditLowerBound));

        switch (input.getType()) {
            case "checking":
            case "savings":
                newAccount.setAccountId(String.valueOf(newStandardAccountNumber) + standardRoutingNumber);
                newAccount.setAccountNumber(newStandardAccountNumber);
                newAccount.setRoutingNumber(standardRoutingNumber);
                newAccount.setBalance(0.0);
                break;
            case "credit":
                newAccount.setAccountId(String.valueOf(newCreditAccountNumber));
                newAccount.setAccountNumber(newCreditAccountNumber);
                newAccount.setCreditLimit(input.getCreditLimit());
                newAccount.setBalance(0.0);
                break;
            case "external":
                newAccount.setAccountId(String.valueOf(input.getAccountNumber()) + input.getRoutingNumber());
                newAccount.setAccountNumber(input.getAccountNumber());
                newAccount.setRoutingNumber(input.getRoutingNumber());
                break;
        }

        boolean idNumberAlreadyExists = customer.getAccounts()
                .stream()
                .map(a -> a.getAccountId())
                .collect(Collectors.toSet())
                .contains(newAccount.getAccountId());
        boolean nicknameAlreadyExists = customer.getAccounts()
                .stream()
                .map(a -> a.getNickname())
                .collect(Collectors.toSet())
                .contains(newAccount.getNickname());

        if (idNumberAlreadyExists) {
            result.setError("AccountId Already In Use");
            System.out.println(result);
            return result;
        }

        if (nicknameAlreadyExists) {
            result.setError("Account Nickname Already In Use");
            System.out.println(result);
            return result;
        }

        customer.getAccounts().add(newAccount);
        CustomerDao.save(customer);
        result.setAccount(newAccount);
        result.setMessage("Account Created");

        System.out.println(result);
        return result;
    }
}
