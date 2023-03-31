package michaelpotocar.projectkitty.requests;

public class GetCustomerAccountRequest {
    private Long customerId;
    private String accountId;

    public GetCustomerAccountRequest() {
    }

    public GetCustomerAccountRequest(Long customerId, String accountNumber) {
        this.setCustomerId(customerId);
        this.setAccountId(accountNumber);
    }

    public String getAccountId() {return accountId;}
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

}
