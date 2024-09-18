package br.com.spotifyjvcw.gateway.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.exception.especific.FailCreateTokenException;
import br.com.spotifyjvcw.gateway.SpotifyAuthGateway;
import br.com.spotifyjvcw.gateway.data.SpotifyTokenResponse;
import br.com.spotifyjvcw.usecase.input.GenerateTokenInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class SpotifyAuthGatewayImpl implements SpotifyAuthGateway {

    @Value("${spotify.authorizationUri}")
    private String authorizationUri;

    @Override
    public Token generateToken(GenerateTokenInput generateTokenInput) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", generateTokenInput.code());
        body.add("redirect_uri", generateTokenInput.redirectUri());
        body.add("client_id", generateTokenInput.clientId());
        body.add("code_verifier", generateTokenInput.codeVerifier());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<SpotifyTokenResponse> response = new RestTemplate().exchange(
                String.format("%s/api/token", authorizationUri),
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                SpotifyTokenResponse.class
        );

        SpotifyTokenResponse spotifyTokenResponse = response.getBody();
        if (response.getStatusCode() != HttpStatus.OK || spotifyTokenResponse == null) {
            throw new FailCreateTokenException("Error generating token!");
        }

        return Token.builder()
                .accessToken(spotifyTokenResponse.accessToken())
                .refreshToken(spotifyTokenResponse.refreshToken())
                .build();
    }
}
