package michaelpotocar.projectkitty.results;

import java.util.List;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Customer;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetCustomersResult {
    @With
    @Getter
    @Setter
    private List<Customer> customers;

}
