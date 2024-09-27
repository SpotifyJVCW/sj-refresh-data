package br.com.spotifyjvcw.gateway.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.gateway.SaveDataGateway;
import br.com.spotifyjvcw.gateway.repository.TokenRepository;
import br.com.spotifyjvcw.gateway.repository.entity.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveDataGatewayImpl implements SaveDataGateway {

    private final TokenRepository tokenRepository;

    @Override
    public Token getToken(String clientId) {
        return tokenRepository.findById(clientId)
                .map(TokenEntity::toDomain)
                .orElse(null);
    }

    @Override
    public List<Token> getAllTokens() {
        List<TokenEntity> tokenEntities = tokenRepository.findAll();
        return tokenEntities.stream().map(TokenEntity::toDomain).toList();
    }

    @Override
    public void refreshToken(Token token) {
        TokenEntity tokenEntity = tokenRepository.findById(token.getClientId())
                        .orElseThrow(() -> new RuntimeException("Token not found"));
        tokenEntity.setRefreshToken(token.getRefreshToken());
        tokenRepository.save(tokenEntity);
    }

    @Override
    public void createToken(Token token) {
        tokenRepository.save(TokenEntity.builder()
                .clientId(token.getClientId())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build());
    }
}
