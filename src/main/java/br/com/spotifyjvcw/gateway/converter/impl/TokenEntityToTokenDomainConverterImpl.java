package br.com.spotifyjvcw.gateway.converter.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.gateway.converter.TokenEntityToTokenDomainConverter;
import br.com.spotifyjvcw.gateway.entity.TokenEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class TokenEntityToTokenDomainConverterImpl implements TokenEntityToTokenDomainConverter {

    @Override
    public List<Token> execute(List<TokenEntity> tokenEntities) {
        return tokenEntities.stream().map(this::execute).collect(Collectors.toList());
    }

    @Override
    public Token execute(TokenEntity tokenEntity) {
        if(isNull(tokenEntity))
            return null;

        return Token.builder()
                .accessToken(tokenEntity.getAccessToken())
                .refreshToken(tokenEntity.getRefreshToken())
                .clientId(tokenEntity.getClientId())
                .build();
    }


}
