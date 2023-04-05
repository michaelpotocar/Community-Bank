package michaelpotocar.projectkitty.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import michaelpotocar.projectkitty.dynamodb.DynamoDbMapperProvider;
import michaelpotocar.projectkitty.dynamodb.model.PeerToPeerTransfer;

import java.util.Collections;
import java.util.List;

public class PeerToPeerTransferDao {

    public static List<PeerToPeerTransfer> getPending(Long customerId) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        PeerToPeerTransfer p2p = new PeerToPeerTransfer()
                .withTargetCustomerId(customerId);

        DynamoDBQueryExpression<PeerToPeerTransfer> queryExpression = (new DynamoDBQueryExpression())
                .withHashKeyValues(p2p)
                .withFilterExpression("attribute_not_exists(completedDateTime)");
        List<PeerToPeerTransfer> p2ps = ddbMapper.query(PeerToPeerTransfer.class, queryExpression);
        return p2ps;

    }

    public static void save(PeerToPeerTransfer p2p) {
        DynamoDBMapper ddbMapper = DynamoDbMapperProvider.getDynamoDbMapper();
        ddbMapper.save(p2p);
    }

}
