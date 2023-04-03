package michaelpotocar.projectkitty.requests;

public class PostCreateAccountRequest {
    private Long customerId;
    private String type;
    private String nickname;
    private Long accountNumber;
    private Long routingNumber;
    private Long creditLimit;

    public PostCreateAccountRequest() {
    }

    public PostCreateAccountRequest(Long customerId, String type, String nickname, Long accountNumber, Long routingNumber, Long creditLimit) {
        this.customerId = customerId;
        this.type = type;
        this.nickname = nickname;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.creditLimit = creditLimit;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Long creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public String toString() {
        return "PostCreateAccountRequest{" +
                "customerId=" + customerId +
                ", type='" + type + '\'' +
                ", nickname='" + nickname + '\'' +
                ", accountNumber=" + accountNumber +
                ", routingNumber=" + routingNumber +
                ", creditLimit=" + creditLimit +
                '}';
    }
}
