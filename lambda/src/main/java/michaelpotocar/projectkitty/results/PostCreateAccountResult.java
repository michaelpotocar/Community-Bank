package michaelpotocar.projectkitty.results;

import michaelpotocar.projectkitty.dynamodb.model.Account;

public class PostCreateAccountResult {
    private Account account;
    private String message;

    public PostCreateAccountResult() {
    }

    public PostCreateAccountResult(Account account, String message) {
        this.account = account;
        this.message = message;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PostCreateAccountResult{" +
                "account=" + account +
                ", message='" + message + '\'' +
                '}';
    }
}

