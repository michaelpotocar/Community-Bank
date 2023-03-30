package michaelpotocar.projectkitty.requests;

public class GetTransactionsRequest {

    private Long customerId;
    private Long accountNumber;

    public GetTransactionsRequest() {

    }

    public GetTransactionsRequest(Long customerId, Long accountNumber) {
        this.setCustomerId(customerId);
        this.setAccountNumber(accountNumber);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "GetTransactionsRequest{" +
                "customerId=" + customerId +
                ", accountNumber=" + accountNumber +
                '}';
    }
}
