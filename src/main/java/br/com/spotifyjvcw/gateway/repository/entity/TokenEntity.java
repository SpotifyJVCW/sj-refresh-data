package br.com.spotifyjvcw.gateway.repository.entity;

import br.com.spotifyjvcw.domain.Token;
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

    public Token toDomain() {
        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .clientId(clientId)
                .build();
    }
}
