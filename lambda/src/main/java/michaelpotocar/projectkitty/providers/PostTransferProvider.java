package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.dao.PeerToPeerTransferDao;
import michaelpotocar.projectkitty.dynamodb.dao.TransactionDao;
import michaelpotocar.projectkitty.dynamodb.model.*;
import michaelpotocar.projectkitty.results.PostTransferResult;
import michaelpotocar.projectkitty.requests.PostTransferRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PostTransferProvider implements RequestHandler<PostTransferRequest, PostTransferResult> {
    public PostTransferResult handleRequest(PostTransferRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        PostTransferResult result = new PostTransferResult();

        if (input.getType() == null
                || input.getAmount() == null
                || input.getAmount() <= 0) {
            result.setError("Invalid Request");
            System.out.println(result);
            return result;
        }

        long epochSeconds = System.currentTimeMillis() / 1000L;
        String memo = input.getMemo() != null ? String.format(" - %s", input.getMemo()) : "";

        Customer customer = CustomerDao.get(input.getCustomerId());
        if (customer == null) {
            result.setError("Invalid customerId");
            System.out.println(result);
            return result;
        }
        List<Account> customerAccounts = customer.getAccounts();

        List<String> internalStandardAccountTypes = Arrays.asList("checking", "savings");
        List<String> externalStandardAccountTypes = Arrays.asList("external");

        List<Account> validFundingAccounts = customerAccounts
                .stream()
                .filter(a -> (a.getAccountId().equals(input.getFundingAccountId())
                        && internalStandardAccountTypes.contains(a.getType())
                        && a.getBalance() > input.getAmount())
                        || (a.getAccountId().equals(input.getFundingAccountId())
                        && externalStandardAccountTypes.contains(a.getType())))
                .collect(Collectors.toList());
        if (validFundingAccounts.size() == 0) {
            result.setError("Invalid Funding Account");
            System.out.println(result);
            return result;
        }
        Account fundingAccount = validFundingAccounts.get(0);

        List<String> targetAccountTypes;

        //////////////////////////////Standard Transfer
        if (input.getType().equals("standard")) {
            targetAccountTypes = Arrays.asList("checking", "savings", "external");
            List<Account> validTargetAccounts = customerAccounts
                    .stream()
                    .filter(a -> a.getAccountId().equals(input.getTargetAccountId())
                            && targetAccountTypes.contains(a.getType()))
                    .collect(Collectors.toList());
            if (validTargetAccounts.size() == 0) {
                result.setError("Invalid Destination Account");
                System.out.println(result);
                return result;
            }
            Account targetAccount = validTargetAccounts.get(0);

            if (internalStandardAccountTypes.contains(fundingAccount.getType())) {
                Transaction fundingTransaction = new Transaction()
                        .withAccountId(fundingAccount.getAccountId())
                        .withAmount(-1 * input.getAmount())
                        .withMemo(String.format("Transfer to %s%s", targetAccount.getAccountId(), memo))
                        .withSubmittedDateTime(epochSeconds)
                        .withCompletedDateTime(epochSeconds);
                result.setFundingTransaction(fundingTransaction);
                TransactionDao.save(fundingTransaction);
                fundingAccount.setBalance(fundingAccount.getBalance() - input.getAmount());
            }
            if (internalStandardAccountTypes.contains(targetAccount.getType())) {
                Transaction targetTransaction = new Transaction()
                        .withAccountId(targetAccount.getAccountId())
                        .withAmount(input.getAmount())
                        .withMemo(String.format("Transfer from %s%s", fundingAccount.getAccountId(), memo))
                        .withSubmittedDateTime(epochSeconds)
                        .withCompletedDateTime(epochSeconds);
                result.setTargetTransaction(targetTransaction);
                TransactionDao.save(targetTransaction);
                targetAccount.setBalance(targetAccount.getBalance() + input.getAmount());
            }

            CustomerDao.save(customer);

            result.setMessage("Success");
            System.out.println(result);
            return result;

            //////////////////////////////Pay Credit
        } else if (input.getType().equals("credit")) {

            targetAccountTypes = Arrays.asList("credit");
            List<Account> validTargetAccounts = customerAccounts
                    .stream()
                    .filter(a -> a.getAccountId().equals(input.getTargetAccountId())
                            && targetAccountTypes.contains(a.getType()))
                    .collect(Collectors.toList());
            if (validTargetAccounts.size() == 0) {
                result.setError("Invalid Destination Account");
                System.out.println(result);
                return result;
            }
            Account targetAccount = validTargetAccounts.get(0);

            if (internalStandardAccountTypes.contains(fundingAccount.getType())) {
                Transaction fundingTransaction = new Transaction()
                        .withAccountId(fundingAccount.getAccountId())
                        .withAmount(-1 * input.getAmount())
                        .withMemo(String.format("Payment to %s%s", targetAccount.getAccountId(), memo))
                        .withSubmittedDateTime(epochSeconds)
                        .withCompletedDateTime(epochSeconds);
                result.setFundingTransaction(fundingTransaction);
                TransactionDao.save(fundingTransaction);
                fundingAccount.setBalance(fundingAccount.getBalance() - input.getAmount());
            }

            Transaction targetTransaction = new Transaction()
                    .withAccountId(targetAccount.getAccountId())
                    .withAmount(input.getAmount())
                    .withMemo(String.format("Payment from %s%s", fundingAccount.getAccountId(), memo))
                    .withSubmittedDateTime(epochSeconds)
                    .withCompletedDateTime(epochSeconds);
            result.setTargetTransaction(targetTransaction);
            TransactionDao.save(targetTransaction);
            targetAccount.setBalance(targetAccount.getBalance() + input.getAmount());

            CustomerDao.save(customer);

            result.setMessage("Success");
            System.out.println(result);
            return result;

            //////////////////////////////Peer to Peer Transfer
        } else if (input.getType().equals("p2p")) {

            List<CustomerStub> validContacts = customer.getContacts()
                    .stream()
                    .filter(a -> a.getId().equals(input.getTargetContactId()))
                    .collect(Collectors.toList());
            if (validContacts.size() == 0) {
                result.setError("Invalid Contact");
                System.out.println(result);
                return result;
            }
            CustomerStub targetCustomer = validContacts.get(0);

            if (internalStandardAccountTypes.contains(fundingAccount.getType())) {
                Transaction fundingTransaction = new Transaction()
                        .withAccountId(fundingAccount.getAccountId())
                        .withAmount(-1 * input.getAmount())
                        .withMemo(String.format("Payment to %s%s", targetCustomer.getFullName(), memo))
                        .withSubmittedDateTime(epochSeconds)
                        .withCompletedDateTime(epochSeconds);
                result.setFundingTransaction(fundingTransaction);
                TransactionDao.save(fundingTransaction);
                fundingAccount.setBalance(fundingAccount.getBalance() - input.getAmount());
            }

            CustomerDao.save(customer);

            PeerToPeerTransfer p2p = new PeerToPeerTransfer()
                    .withAmount(input.getAmount())
                    .withMemo(String.format("Payment from %s%s", targetCustomer.getFullName(), memo))
                    .withSubmittedDateTime(epochSeconds)
                    .withFundingCustomerId(customer.getId())
                    .withTargetCustomerId(targetCustomer.getId());

            PeerToPeerTransferDao.save(p2p);

            //////////////////////////////Invalid Transfer Type
        } else {
            result.setError("Invalid Request");
            System.out.println(result);
            return result;
        }

        System.out.println(result);
        return result;
    }
}
