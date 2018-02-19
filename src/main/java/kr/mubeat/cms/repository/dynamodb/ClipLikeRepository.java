package kr.mubeat.cms.repository.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import kr.mubeat.cms.enumerations.DynamoDBTables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

/**
 * Created by moonkyu.lee on 2017. 10. 24..
 */
@Repository
public class ClipLikeRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    public ClipLikeRepository(
            AmazonDynamoDB amazonDynamoDB
    ) {
        this.amazonDynamoDB = amazonDynamoDB;
    }

    public void deleteItemByClipMetaId(Long clipMetaId) {
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table table = dynamoDB.getTable(DynamoDBTables.tbl_clip_like.name());

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("clipmetaid = :v_id")
                .withValueMap(new ValueMap()
                        .withLong(":v_id", clipMetaId));

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item = null;
        while (iterator.hasNext()) {
            item = iterator.next();
            table.deleteItem(
                    new PrimaryKey(
                            "clipmetaid", item.get("clipmetaid"),
                            "userno", item.get("userno")
                    )
            );
        }
    }
}
