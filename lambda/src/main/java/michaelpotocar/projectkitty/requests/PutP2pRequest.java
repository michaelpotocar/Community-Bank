package michaelpotocar.projectkitty.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ToString
public class PutP2pRequest {
    private Long customerId;
    private String targetAccountId;
    private Long submittedDateTime;
}
