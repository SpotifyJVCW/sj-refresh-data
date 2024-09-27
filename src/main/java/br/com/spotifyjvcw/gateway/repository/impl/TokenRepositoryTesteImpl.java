package br.com.spotifyjvcw.gateway.repository.impl;

import br.com.spotifyjvcw.gateway.repository.TokenRepository;
import br.com.spotifyjvcw.gateway.repository.entity.TokenEntity;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Profile("test")
public class TokenRepositoryTesteImpl implements TokenRepository {

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.clientid-test}")
    private String clientIdTest;

    private DynamoDBMapper mapper;

    @PostConstruct
    public void construct() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonDynamoDB db = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        mapper = new DynamoDBMapper(db);
    }

    @Override
    public Optional<TokenEntity> findById(String clientId) {
        try {
            TokenEntity tokenEntity = mapper.load(TokenEntity.class, clientIdTest);
            return Optional.ofNullable(tokenEntity);
        } catch (Exception e) {
            log.error("Error getting item from DynamoDB: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<TokenEntity> findAll() {
        try {
            DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
            return mapper.scan(TokenEntity.class, dynamoDBScanExpression).stream()
                    .filter(tokenEntity -> tokenEntity.getClientId().equals(clientIdTest)).toList();
        } catch (Exception e) {
            log.error("Error getting all items from DynamoDB: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public TokenEntity save(TokenEntity tokenEntity) {
        try {
            mapper.save(tokenEntity);
            return tokenEntity;
        } catch (Exception e) {
            log.error("Error saving item to DynamoDB: {}", e.getMessage());
            return null;
        }
    }
}
