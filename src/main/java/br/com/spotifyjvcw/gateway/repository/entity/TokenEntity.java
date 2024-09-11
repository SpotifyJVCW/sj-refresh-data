package br.com.spotifyjvcw.gateway.repository.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Data
@DynamoDBTable(tableName = "token")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {

    @DynamoDBHashKey(attributeName = "clientId")
    private String clientId;
    @DynamoDBAttribute(attributeName = "accessToken")
    private String accessToken;
    @DynamoDBAttribute(attributeName = "refreshToken")
    private String refreshToken;
}
