package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Customer;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetCustomerResult {
    @With
    @Getter
    @Setter
    private Customer customer;

}
