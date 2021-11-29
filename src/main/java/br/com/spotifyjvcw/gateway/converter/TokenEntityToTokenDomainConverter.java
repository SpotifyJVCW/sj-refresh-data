package br.com.spotifyjvcw.gateway.converter;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.gateway.entity.TokenEntity;

public interface TokenEntityToTokenDomainConverter {

    Token execute(TokenEntity tokenEntity);
}
