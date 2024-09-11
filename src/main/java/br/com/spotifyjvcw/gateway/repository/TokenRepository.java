package br.com.spotifyjvcw.gateway.repository;

import br.com.spotifyjvcw.gateway.repository.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, String> {
}
