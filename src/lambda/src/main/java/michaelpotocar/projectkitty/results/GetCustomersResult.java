package michaelpotocar.projectkitty.results;

import michaelpotocar.projectkitty.dynamodb.model.AccountStub;
import michaelpotocar.projectkitty.dynamodb.model.ContactStub;
import michaelpotocar.projectkitty.javaModel.Account;

import java.util.List;

public class GetCustomerInfoResult {
    private Long id;
    private List<AccountStub> accounts;
    private String firstName;
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AccountStub> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountStub> accounts) {
        this.accounts = accounts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
