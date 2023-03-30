package michaelpotocar.projectkitty.requests;

public class GetCustomerAccountsRequest {
    private Long customerId;

    public GetCustomerAccountsRequest() {
    }

    public GetCustomerAccountsRequest(Long customerId) {
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
