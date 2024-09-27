package br.com.spotifyjvcw.gateway;

import br.com.spotifyjvcw.domain.Token;
import java.util.List;

public interface SaveDataGateway {

    Token getToken(String clientId);
    List<Token> getAllTokens();
    void refreshToken(Token token);
    void createToken(Token token);
}
