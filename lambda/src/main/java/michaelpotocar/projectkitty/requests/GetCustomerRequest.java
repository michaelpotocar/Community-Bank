package michaelpotocar.projectkitty.requests;

public class GetCustomerRequest {

    private Long customerId;

    public GetCustomerRequest() {

    }

    public GetCustomerRequest(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "GetCustomerRequest{" +
                "customerId=" + customerId +
                '}';
    }
}
