package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Customer;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class GetCustomerResult {
    private Customer customer;
    private String message;
    private String error;

}
