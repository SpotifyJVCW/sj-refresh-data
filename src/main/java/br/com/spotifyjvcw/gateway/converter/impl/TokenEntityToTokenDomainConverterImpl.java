package br.com.spotifyjvcw.gateway.converter.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.gateway.converter.TokenEntityToTokenDomainConverter;
import br.com.spotifyjvcw.gateway.entity.TokenEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class TokenEntityToTokenDomainConverterImpl implements TokenEntityToTokenDomainConverter {

    @Override
    public Token execute(TokenEntity tokenEntity) {
        if(isNull(tokenEntity))
            return null;

        return Token.builder()
                .accessToken(tokenEntity.getAccessToken())
                .refreshToken(tokenEntity.getRefreshToken())
                .build();
    }
}