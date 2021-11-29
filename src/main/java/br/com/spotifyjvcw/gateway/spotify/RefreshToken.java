package br.com.spotifyjvcw.gateway.spotify;

import br.com.spotifyjvcw.gateway.entity.TokenEntity;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERefreshRequest;
import lombok.extern.java.Log;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

@Log
public class RefreshToken {

    private final AuthorizationCodePKCERefreshRequest authorizationCodePKCERefreshRequest;

    public TokenEntity generateValidToken() {
        try {
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERefreshRequest.execute();

            log.info("Access: " + authorizationCodeCredentials.getAccessToken());
            log.info("Refresh: " + authorizationCodeCredentials.getRefreshToken());


            return TokenEntity.builder()
                    .accessToken(authorizationCodeCredentials.getAccessToken())
                    .refreshToken(authorizationCodeCredentials.getRefreshToken()).build();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
            return null;
        }
    }

    public RefreshToken(String clientId, String refreshToken){

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setRefreshToken(refreshToken)
                .build();

        authorizationCodePKCERefreshRequest = spotifyApi.authorizationCodePKCERefresh().build();
    }
}
