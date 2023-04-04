package michaelpotocar.projectkitty.requests;

import michaelpotocar.projectkitty.results.PostTransferResult;

public class PostTransferRequest {
    private Long customerId;
    private String type;
    private String fundingAccount;
    private String targetAccount;
    private Long contactId;
    private Double amount;
    private String memo;

    public PostTransferRequest() {
    }

    public PostTransferRequest(Long customerId, String type, String fundingAccount, String targetAccount, Long contactId, Double amount, String memo) {
        this.customerId = customerId;
        this.type = type;
        this.fundingAccount = fundingAccount;
        this.targetAccount = targetAccount;
        this.contactId = contactId;
        this.amount = amount;
        this.memo = memo;
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

    public String getFundingAccount() {
        return fundingAccount;
    }

    public void setFundingAccount(String fundingAccount) {
        this.fundingAccount = fundingAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isNotValid() {

        if (this.getType() == null
                || this.getCustomerId() == null
                || this.getFundingAccount() == null
                || this.getAmount() == null
                || this.getAmount() <= 0
        )
            return true;

        switch (this.getType()) {
            case "standard":
                if (this.getTargetAccount() == null) return true;
                break;
            case "credit":
                if (this.getTargetAccount() == null) return true;
                break;
            case "p2p":
                if (this.getContactId() == null) return true;
                break;
            default:
                return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "PostTransferRequest{" +
                "customerId=" + customerId +
                ", type='" + type + '\'' +
                ", fundingAccount=" + fundingAccount +
                ", targetAccount=" + targetAccount +
                ", contactId=" + contactId +
                ", amount=" + amount +
                ", memo='" + memo + '\'' +
                '}';
    }
}
