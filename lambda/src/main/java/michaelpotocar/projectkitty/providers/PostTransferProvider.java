package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.dao.PeerToPeerTransferDao;
import michaelpotocar.projectkitty.dynamodb.dao.TransactionDao;
import michaelpotocar.projectkitty.dynamodb.model.*;
import michaelpotocar.projectkitty.requests.PostTransferRequest;
import michaelpotocar.projectkitty.results.PostTransferResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PostTransferProvider implements RequestHandler<PostTransferRequest, PostTransferResult> {
    public PostTransferResult handleRequest(PostTransferRequest input, Context context) {
        System.out.println(input);
        PostTransferResult result = new PostTransferResult();

        //Basic Checks
        if (input.getType() == null) {
            result.setError("Invalid Type");
            System.out.println(result);
            return result;
        }
        if (input.getFundingAccountId().equals(input.getTargetAccountId())) {
            result.setError("FundingAccountId cannot match TargetAccountId");
            System.out.println(result);
            return result;
        }
        if (input.getAmount() == null) {
            result.setError("Invalid Amount");
            System.out.println(result);
            return result;
        }
        if (input.getAmount() <= 0) {
            result.setError("Negative Amount");
            System.out.println(result);
            return result;
        }
        Customer customer = CustomerDao.get(input.getCustomerId());
        if (customer == null) {
            result.setError("Invalid customerId");
            System.out.println(result);
            return result;
        }

        List<Account> customerAccounts = customer.getAccounts();
        List<CustomerStub> customerContacts = customer.getContacts();

        Account fundingAccount = customerAccounts
                .stream()
                .filter(a -> a.getAccountId().equals(input.getFundingAccountId()))
                .collect(Collectors.toList())
                .get(0);
        Account targetAccount = null;
        CustomerStub targetContact = null;
        Transaction fundingTransaction = null;

        List<String> intraTransactionalTypes = Arrays.asList("standard", "credit");
        List<String> interTransactionalTypes = Arrays.asList("p2p");

        String memo = input.getMemo() != null ? String.format(" - %s", input.getMemo()) : "";
        String fundingAccountmemo;
        if (intraTransactionalTypes.contains(input.getType())) {
            try {
                targetAccount = customerAccounts
                        .stream()
                        .filter(a -> a.getAccountId().equals(input.getTargetAccountId()))
                        .collect(Collectors.toList())
                        .get(0);
            } catch (IndexOutOfBoundsException e) {
                result.setError("Invalid TargetAccountId");
                System.out.println(result);
                return result;
            }
            fundingAccountmemo = String.format("Transfer to %s%s", targetAccount.getAccountId(), memo);
        } else if (interTransactionalTypes.contains(input.getType())) {
            try {
                targetContact = customerContacts
                        .stream()
                        .filter(a -> a.getId().equals(input.getTargetContactId()))
                        .collect(Collectors.toList())
                        .get(0);
            } catch (IndexOutOfBoundsException e) {
                result.setError("Invalid TargetContactId");
                System.out.println(result);
                return result;
            }
            fundingAccountmemo = String.format("Payment to %s%s", targetContact.getFullName(), memo);
        } else {
            result.setError("Invalid Type");
            System.out.println(result);
            return result;
        }

        List<String> standardAccountTypes = Arrays.asList("checking", "savings", "external");
        List<String> internalStandardAccountTypes = Arrays.asList("checking", "savings");
        List<String> creditAccountTypes = Arrays.asList("credit");
        if (!standardAccountTypes.contains(fundingAccount.getType())) {
            result.setError("Invalid AccountId");
            System.out.println(result);
            return result;
        }
        if (internalStandardAccountTypes.contains(fundingAccount.getType())
                && fundingAccount.getBalance() < input.getAmount()) {
            result.setError("Insufficient Funding Account Balance");
            System.out.println(result);
            return result;
        }

        long epochSeconds = System.currentTimeMillis() / 1000L;
        if (internalStandardAccountTypes.contains(fundingAccount.getType())) {
            fundingTransaction = new Transaction()
                    .withAccountId(fundingAccount.getAccountId())
                    .withAmount(-1 * input.getAmount())
                    .withMemo(fundingAccountmemo)
                    .withSubmittedDateTime(epochSeconds)
                    .withCompletedDateTime(epochSeconds);
            result.setFundingTransaction(fundingTransaction);
            fundingAccount.setBalance(fundingAccount.getBalance() - input.getAmount());
        }

        //////////////////////////////Standard Transfer
        if (input.getType().equals("standard")) {
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
                result.setTargetTransaction(targetTransaction);
            }
            //////////////////////////////Pay Credit
        } else if (input.getType().equals("credit")) {
            if (!creditAccountTypes.contains(targetAccount.getType())) {
                result.setError("Target Account Type Should Be Credit");
                System.out.println(result);
                return result;
            }

            Transaction targetTransaction = new Transaction()
                    .withAccountId(targetAccount.getAccountId())
                    .withAmount(-1 * input.getAmount())
                    .withMemo(String.format("Payment from %s%s", fundingAccount.getAccountId(), memo))
                    .withSubmittedDateTime(epochSeconds)
                    .withCompletedDateTime(epochSeconds);
            result.setTargetTransaction(targetTransaction);
            TransactionDao.save(targetTransaction);
            targetAccount.setBalance(targetAccount.getBalance() - input.getAmount());
            result.setTargetTransaction(targetTransaction);

            //////////////////////////////Peer to Peer Transfer
        } else if (input.getType().equals("p2p")) {
            PeerToPeerTransfer p2p = new PeerToPeerTransfer()
                    .withAmount(input.getAmount())
                    .withMemo(input.getMemo())
                    .withTransferId(epochSeconds)
                    .withFundingCustomerId(customer.getId())
                    .withTargetCustomerId(targetContact.getId());
            PeerToPeerTransferDao.save(p2p);
            result.setP2p(p2p);
            //////////////////////////////Invalid Transfer Type
        }

        result.setMessage("Success");
        TransactionDao.save(fundingTransaction);
        CustomerDao.save(customer);
        System.out.println(result);
        return result;
    }
}
