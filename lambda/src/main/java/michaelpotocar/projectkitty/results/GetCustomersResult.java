package michaelpotocar.projectkitty.results;

import java.util.List;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Customer;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class GetCustomersResult {
    private List<Customer> customers;
    private String message;
    private String error;

}
