package br.com.spotifyjvcw.gateway.converter;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.gateway.repository.entity.TokenEntity;

import java.util.List;

public interface TokenEntityToTokenDomainConverter {

    Token execute(TokenEntity tokenEntity);
    List<Token> execute(List<TokenEntity> tokenEntities);
}
