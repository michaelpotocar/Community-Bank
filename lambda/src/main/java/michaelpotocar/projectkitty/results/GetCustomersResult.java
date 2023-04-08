package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Customer;

import java.util.List;

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
