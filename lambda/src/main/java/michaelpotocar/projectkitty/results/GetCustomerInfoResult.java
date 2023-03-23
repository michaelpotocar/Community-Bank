//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package michaelpotocar.projectkitty.results;

import java.util.List;
import michaelpotocar.projectkitty.dynamodb.model.AccountStub;

public class GetCustomerInfoResult {
    private Long id;
    private List<AccountStub> accounts;
    private String firstName;
    private String lastName;

    public GetCustomerInfoResult() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
