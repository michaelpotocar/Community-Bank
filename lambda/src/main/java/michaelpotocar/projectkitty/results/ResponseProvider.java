package michaelpotocar.projectkitty.results;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class ResponseProvider {
    public static APIGatewayProxyResponseEvent getResponse(String body, int statusCode) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setBody(String.format("{\"message\": \"%s\"}", body));
        response.setStatusCode(statusCode);
        return response;
    }
}
