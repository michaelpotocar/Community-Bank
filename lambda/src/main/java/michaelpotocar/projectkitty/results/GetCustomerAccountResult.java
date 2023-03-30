package michaelpotocar.projectkitty.results;

import michaelpotocar.projectkitty.dynamodb.model.Account;

public class GetCustomerAccountResult {
    private Account account;

    public GetCustomerAccountResult() {
    }

    public GetCustomerAccountResult(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "GetAccountResult{" +
                "account=" + account +
                '}';
    }
}
