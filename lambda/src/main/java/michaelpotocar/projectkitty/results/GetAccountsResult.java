package michaelpotocar.projectkitty.results;

import java.util.List;

import michaelpotocar.projectkitty.dynamodb.model.AccountStub;

public class GetAccountsResult {
    private Long customerId;
    private List<AccountStub> accounts;
    private String firstName;
    private String lastName;

    public GetAccountsResult() {
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<AccountStub> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(List<AccountStub> accounts) {
        this.accounts = accounts;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "GetCustomerInfoResult{" +
                "id=" + customerId +
                ", accounts=" + accounts +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
