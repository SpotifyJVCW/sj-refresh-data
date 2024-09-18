package br.com.spotifyjvcw.gateway;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.usecase.input.GenerateTokenInput;

public interface SpotifyAuthGateway {

    Token generateToken(GenerateTokenInput generateTokenInput);
}
