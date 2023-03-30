package michaelpotocar.projectkitty.requests;

public class GetCustomerAccountRequest {
    private Long accountNumber;
    private Long routingNumber;

    public GetCustomerAccountRequest() {
    }

    public GetCustomerAccountRequest(Long accountNumber, Long routingNumber) {
        this.setAccountNumber(accountNumber);
        this.setRoutingNumber(routingNumber);
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(Long routingNumber) {
        this.routingNumber = routingNumber;
    }

    @Override
    public String toString() {
        return "GetCustomerAccountRequest{" +
                "accountNumber=" + accountNumber +
                ", routingNumber=" + routingNumber +
                '}';
    }
}
