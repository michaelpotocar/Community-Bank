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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PostTransferProvider implements RequestHandler<PostTransferRequest, PostTransferResult> {
    public PostTransferProvider() {
    }

    public PostTransferResult handleRequest(PostTransferRequest input, Context context) {
        System.out.println("Input: " + input.toString());

        if (input.getType() == null
                || input.getCustomerId() == null
                || input.getFundingAccount() == null
                || input.getAmount() == null
                || input.getAmount() <= 0
        ) {
            PostTransferResult result = new PostTransferResult("Invalid Request");
            System.out.println("Result: " + result);
            return result;
        }

        Customer customer;
        ///////////////Standard Transfer
        if (input.getType().equals("standard")) {
            if (input.getTargetAccount() == null) {
                PostTransferResult result = new PostTransferResult("Null Destination Account");
                System.out.println("Result: " + result);
                return result;
            }

            customer = CustomerDao.getCustomer(input.getCustomerId());
            if (customer == null) {
                PostTransferResult result = new PostTransferResult("Invalid customerId");
                System.out.println("Result: " + result);
                return result;
            }

            List<String> validInternalFundingAccountTypes = Arrays.asList("checking", "savings");
            List<String> validExternalAccountTypes = Arrays.asList("external");
            List<Account> customerAccounts = customer.getAccounts();
            List<Account> validFundingAccounts = customerAccounts
                    .stream()
                    .filter(a -> (a.getAccountId().equals(input.getFundingAccount())
                            && validInternalFundingAccountTypes.contains(a.getType())
                            && a.getBalance() > input.getAmount())
                            || (a.getAccountId().equals(input.getFundingAccount())
                            && validExternalAccountTypes.contains(a.getType())))
                    .collect(Collectors.toList());
            if (validFundingAccounts.size() == 0) {
                PostTransferResult result = new PostTransferResult("Invalid Funding Account");
                System.out.println("Result: " + result);
                return result;
            }
            Account fundingAccount = validFundingAccounts.get(0);

            List<String> validTargetAccountTypes = Arrays.asList("checking", "savings", "external");
            List<Account> validTargetAccounts = customerAccounts
                    .stream()
                    .filter(a -> a.getAccountId().equals(input.getTargetAccount())
                            && validTargetAccountTypes.contains(a.getType()))
                    .collect(Collectors.toList());
            if (validTargetAccounts.size() == 0) {
                PostTransferResult result = new PostTransferResult("Invalid Destination Account");
                System.out.println("Result: " + result);
                return result;
            }
            Account targetAccount = validTargetAccounts.get(0);

            Transaction originTransaction = new Transaction();
            Transaction destinationTransaction = new Transaction();

            originTransaction.withAccountId("");

            ///////////////Pay Credit
        } else if (input.getType().equals("credit")) {
            if (input.getTargetAccount() == null) {
                PostTransferResult result = new PostTransferResult("Null Destination Account");
                System.out.println("Result: " + result);
                return result;
            }

            customer = CustomerDao.getCustomer(input.getCustomerId());
            if (customer == null) {
                PostTransferResult result = new PostTransferResult("Invalid customerId");
                System.out.println("Result: " + result);
                return result;
            }


            ///////////////Peer to Peer Transfer
        } else if (input.getType().equals("p2p")) {
            if (input.getContactId() == null) {
                PostTransferResult result = new PostTransferResult("Null Contact");
                System.out.println("Result: " + result);
                return result;
            }

            customer = CustomerDao.getCustomer(input.getCustomerId());
            if (customer == null) {
                PostTransferResult result = new PostTransferResult("Invalid customerId");
                System.out.println("Result: " + result);
                return result;
            }

        } else {
            PostTransferResult result = new PostTransferResult("Invalid Request");
            System.out.println("Result: " + result);
            return result;
        }



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
