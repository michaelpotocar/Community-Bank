package michaelpotocar.projectkitty.results;

import java.util.List;

import michaelpotocar.projectkitty.dynamodb.model.Account;

public class GetCustomerAccountsResult {
    private List<Account> accounts;

    public GetCustomerAccountsResult() {
    }

    public GetCustomerAccountsResult(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "GetAccountsResult{" +
                "accounts=" + accounts +
                '}';
    }
}
