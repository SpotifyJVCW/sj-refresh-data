package br.com.spotifyjvcw.gateway.repository;

import br.com.spotifyjvcw.gateway.repository.entity.TokenEntity;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    Optional<TokenEntity> findById(String clientId);
    List<TokenEntity> findAll();
    TokenEntity save(TokenEntity tokenEntity);
}
