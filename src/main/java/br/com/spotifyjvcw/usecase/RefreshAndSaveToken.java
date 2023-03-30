package br.com.spotifyjvcw.usecase;

import br.com.spotifyjvcw.domain.Token;

public interface RefreshAndSaveToken {

    Token execute(String clientId);
}
