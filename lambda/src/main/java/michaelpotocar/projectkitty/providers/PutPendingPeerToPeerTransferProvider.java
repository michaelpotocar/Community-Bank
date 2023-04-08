package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.dao.PeerToPeerTransferDao;
import michaelpotocar.projectkitty.dynamodb.dao.TransactionDao;
import michaelpotocar.projectkitty.dynamodb.model.*;
import michaelpotocar.projectkitty.requests.PutPendingPeerToPeerTransferRequest;
import michaelpotocar.projectkitty.results.PutPendingPeerToPeerTransferResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PutPendingPeerToPeerTransferProvider implements RequestHandler<PutPendingPeerToPeerTransferRequest, PutPendingPeerToPeerTransferResult> {
    public PutPendingPeerToPeerTransferResult handleRequest(PutPendingPeerToPeerTransferRequest input, Context context) {
        System.out.println(input);
        PutPendingPeerToPeerTransferResult result = new PutPendingPeerToPeerTransferResult();

        if (input.getCustomerId() == null) {
            result.setError("Invalid CustomerId");
            System.out.println(result);
            return result;
        }
        if (input.getTargetAccountId() == null) {
            result.setError("Invalid TargetAccountId");
            System.out.println(result);
            return result;
        }
        if (input.getTransferId() == null) {
            result.setError("Invalid TransferId");
            System.out.println(result);
            return result;
        }

        Customer customer = CustomerDao.get(input.getCustomerId());
        if (customer == null) {
            result.setError("Invalid CustomerId");
            System.out.println(result);
            return result;
        }

        List<Account> customerAccounts = customer.getAccounts();

        List<String> validTargetAccountTypes = Arrays.asList("checking", "savings", "external");

        Account targetAccount = null;
        try {
            targetAccount = customerAccounts
                    .stream()
                    .filter(a -> a.getAccountId().equals(input.getTargetAccountId())
                            && validTargetAccountTypes.contains(a.getType()))
                    .collect(Collectors.toList())
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            result.setError("Invalid TargetAccountId");
            System.out.println(result);
            return result;
        }

        PeerToPeerTransfer p2p = null;
        List<PeerToPeerTransfer> pendingP2pTransfers = PeerToPeerTransferDao.getPending(input.getCustomerId());
        try {
            p2p = pendingP2pTransfers
                    .stream()
                    .filter(t -> t.getTransferId().equals(input.getTransferId()))
                    .collect(Collectors.toList())
                    .get(0);

        } catch (IndexOutOfBoundsException e) {
            result.setError("Invalid P2P");
            System.out.println(result);
            return result;
        }

        long epochSeconds = System.currentTimeMillis() / 1000L;
        String memo = p2p.getMemo() != null ? String.format(" - %s", p2p.getMemo()) : "";

        long fundingCustomerId = p2p.getFundingCustomerId();
        CustomerStub sender = customer.getContacts()
                .stream()
                .filter(c -> c.getId().equals(fundingCustomerId))
                .collect(Collectors.toList())
                .get(0);


        List<String> internalStandardAccountTypes = Arrays.asList("checking", "savings");

        if (internalStandardAccountTypes.contains(targetAccount.getType())) {
            Transaction targetTransaction = new Transaction()
                    .withAccountId(targetAccount.getAccountId())
                    .withAmount(p2p.getAmount())
                    .withMemo(String.format("Transfer from %s%s", sender.getFullName(), memo))
                    .withSubmittedDateTime(epochSeconds)
                    .withCompletedDateTime(epochSeconds);
            result.setTargetTransaction(targetTransaction);
            TransactionDao.save(targetTransaction);
            targetAccount.setBalance(targetAccount.getBalance() + p2p.getAmount());
            CustomerDao.save(customer);
        }

        p2p.setCompletedDateTime(epochSeconds);

        PeerToPeerTransferDao.save(p2p);

        result.setMessage("Success");
        System.out.println(result);
        return result;

    }
}
