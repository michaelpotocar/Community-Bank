package michaelpotocar.projectkitty.requests;

public class GetTransactionsRequest {

    private Long customerId;
    private Long accountId;

    public GetTransactionsRequest() {

    }

    public GetTransactionsRequest(Long customerId, Long accountId) {
        this.setCustomerId(customerId);
        this.setAccountId(accountId);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "GetTransactionsRequest{" +
                "customerId=" + customerId +
                ", accountId=" + accountId +
                '}';
    }
}
