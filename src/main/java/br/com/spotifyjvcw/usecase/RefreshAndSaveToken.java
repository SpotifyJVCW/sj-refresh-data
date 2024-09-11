package br.com.spotifyjvcw.usecase;

import br.com.spotifyjvcw.domain.Token;

import java.util.List;

public interface RefreshAndSaveToken {

    Token execute(String clientId);
    List<Token> executeAll();
}
