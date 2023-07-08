package project.shortlink.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "link")
public class Link {
    @DynamoDBHashKey
    private String shortId;
    @DynamoDBAttribute
    private String originalUrl;
    @DynamoDBAttribute
    private String createAt;
}
