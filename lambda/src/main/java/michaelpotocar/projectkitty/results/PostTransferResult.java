package michaelpotocar.projectkitty.results;

public class PostTransferResult {
    private String message;

    public PostTransferResult() {
    }

    public PostTransferResult(String message) {
        this.message = message;
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
                ", message='" + message + '\'' +
                '}';
    }
}

