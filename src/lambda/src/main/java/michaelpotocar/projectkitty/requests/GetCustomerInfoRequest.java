package michaelpotocar.projectkitty.requests;

public class GetCustomerInfoRequest {
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GetCustomerInfoRequest(String id) {
        this.setId(id);
    }
}
