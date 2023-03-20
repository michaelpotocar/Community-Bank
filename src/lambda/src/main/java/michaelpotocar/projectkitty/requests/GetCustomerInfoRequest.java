package michaelpotocar.projectkitty.requests;

public class GetCustomerInfoRequest {
    private Long id;

    public GetCustomerInfoRequest() {
    }

    public GetCustomerInfoRequest(Long id) {
        this.setId(id);
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
