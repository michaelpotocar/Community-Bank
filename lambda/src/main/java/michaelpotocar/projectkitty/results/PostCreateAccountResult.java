package michaelpotocar.projectkitty.results;

import lombok.*;
import michaelpotocar.projectkitty.dynamodb.model.Account;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostCreateAccountResult {
    @With
    @Getter
    @Setter
    private Account account;
    @With
    @Getter
    @Setter
    private String message;

}

