package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.gateway.SaveDataGateway;
import br.com.spotifyjvcw.gateway.SpotifyAuthGateway;
import br.com.spotifyjvcw.gateway.spotify.SpotifyCallApi;
import br.com.spotifyjvcw.usecase.ValidateAuthentication;
import br.com.spotifyjvcw.usecase.input.GenerateTokenInput;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.model_objects.specification.User;

@Component
@RequiredArgsConstructor
public class ValidateAuthenticationImpl implements ValidateAuthentication {

    private final SpotifyAuthGateway spotifyAuthGateway;
    private final SaveDataGateway saveDataGateway;

    private static final String CODE_CHALLENGE_METHOD = "S256";
    private static final String SCOPE = "playlist-modify-private playlist-modify-public user-top-read";
    private static final String RESPONSE_TYPE = "code";

    @Value("${spotify.clientIdApplication}")
    private String clientIdApplication;

    @Value("${spotify.redirectUri}")
    private String redirectUri;

    @Value("${spotify.authorizationUri}")
    private String authorizationUri;

    @Value("${spotify.codeChallenge}")
    private String codeChallenge;

    @Value("${spotify.codeVerifier}")
    private String codeVerifier;

    @Override
    public String createSpotifyUrlNewUser() {
        return String.format("%s/authorize?client_id=%s&response_type=%s&redirect_uri=%s/users/generate-token&code_challenge=%s&code_challenge_method=%s&scope=%s",
                authorizationUri, clientIdApplication, RESPONSE_TYPE, redirectUri, codeChallenge, CODE_CHALLENGE_METHOD, SCOPE);
    }

    @Override
    public void generateToken(String code) {
        GenerateTokenInput generateTokenInput = new GenerateTokenInput(code, clientIdApplication, codeVerifier, redirectUri + "/users/generate-token");
        Token token = spotifyAuthGateway.generateToken(generateTokenInput);
        User user = SpotifyCallApi.getUserInformation(token.getAccessToken());
        token.setClientId(user.getId());
        saveDataGateway.createToken(token);
    }
}
