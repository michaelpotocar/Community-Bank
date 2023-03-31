package michaelpotocar.projectkitty.requests;

public class GetAccountTransactionsRequest {

    private Long customerId;
    private String accountId;

    public GetAccountTransactionsRequest() {

    }

    public GetAccountTransactionsRequest(Long customerId, String accountId) {
        this.setCustomerId(customerId);
        this.setAccountId(accountId);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "GetTransactionsRequest{" +
                "customerId=" + customerId +
                ", accountNumber=" + accountId +
                '}';
    }
}
