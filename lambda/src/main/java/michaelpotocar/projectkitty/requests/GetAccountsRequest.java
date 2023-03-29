package michaelpotocar.projectkitty.requests;

public class GetAccountsRequest {
    private Long customerId;

    public GetAccountsRequest() {
    }

    public GetAccountsRequest(Long customerId) {
        this.setCustomerId(customerId);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "GetCustomerInfoRequest{" +
                "customerId=" + customerId +
                '}';
    }
}
