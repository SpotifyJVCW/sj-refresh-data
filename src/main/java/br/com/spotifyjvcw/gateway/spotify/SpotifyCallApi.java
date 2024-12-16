package br.com.spotifyjvcw.gateway.spotify;

import br.com.spotifyjvcw.domain.Token;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.AbstractRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERefreshRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.ReplacePlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class SpotifyCallApi<C extends AbstractRequest<R>, R> {

    private final SpotifyApi spotifyApi;
    private C call;

    public SpotifyCallApi(String accessToken) {
        spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
    }

    public SpotifyCallApi(String refreshToken, String applicationClientId) {
        spotifyApi = new SpotifyApi.Builder().setClientId(applicationClientId).setRefreshToken(refreshToken).build();
    }

    public Optional<R> execute() {
        try {
            return Optional.of(call.execute());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public SpotifyApi builder() {
        return this.spotifyApi;
    }

    public SpotifyCallApi<C, R> call(C call) {
        this.call = call;
        return this;
    }

    public static PlaylistSimplified[] getCurrentPlaylists(String accessToken, int limit, int offset) {
        SpotifyCallApi<GetListOfCurrentUsersPlaylistsRequest, Paging<PlaylistSimplified>> spotifyCallApi = new SpotifyCallApi<>(accessToken);
        return spotifyCallApi.call(spotifyCallApi.builder()
                        .getListOfCurrentUsersPlaylists()
                        .limit(limit).offset(offset)
                        .build())
                .execute().map(Paging::getItems).orElse(new PlaylistSimplified[0]);
    }

    public static Playlist createPlaylist(String accessToken, String clientId, String playlistName) {
        SpotifyCallApi<CreatePlaylistRequest, Playlist> spotifyCallApi = new SpotifyCallApi<>(accessToken);
        return spotifyCallApi.call(spotifyCallApi.builder()
                        .createPlaylist(clientId, playlistName)
                        .build())
                .execute().orElse(new Playlist.Builder().build());
    }

    public static void updatePlaylist(String accessToken, String playlistId, String[] trackIds) {
        SpotifyCallApi<ReplacePlaylistsItemsRequest, String> spotifyCallApi = new SpotifyCallApi<>(accessToken);
        spotifyCallApi.call(spotifyCallApi.builder()
                        .replacePlaylistsItems(playlistId, trackIds)
                            .setBodyParameter("range_start", 0)
                            .setBodyParameter("insert_before", 0)
                            .setBodyParameter("range_lengh", trackIds.length)
                        .build())
                .execute();
    }

    public static Track[] getTopTracksByTerm(String term, String accessToken, Integer quantity) {
        SpotifyCallApi<GetUsersTopTracksRequest, Paging<Track>> spotifyCallApi = new SpotifyCallApi<>(accessToken);
        return spotifyCallApi.call(spotifyCallApi.builder()
                        .getUsersTopTracks()
                            .limit(quantity)
                            .time_range(term)
                        .build())
                .execute().map(Paging::getItems).orElse(new Track[0]);
    }

    public static User getUserInformation(String accessToken) {
        SpotifyCallApi<GetCurrentUsersProfileRequest, User> spotifyCallApi = new SpotifyCallApi<>(accessToken);
        return spotifyCallApi.call(spotifyCallApi.builder()
                        .getCurrentUsersProfile()
                        .build())
                .execute().orElse(null);
    }

    public static Token refreshToken(String refreshToken, String clientIdApplication) {
        SpotifyCallApi<AuthorizationCodePKCERefreshRequest, AuthorizationCodeCredentials> spotifyCallApi = new SpotifyCallApi<>(refreshToken, clientIdApplication);
        return spotifyCallApi.call(spotifyCallApi.builder()
                        .authorizationCodePKCERefresh()
                        .build())
                .execute().map(SpotifyCallApi::convertToToken).orElse(null);
    }

    private static Token convertToToken(AuthorizationCodeCredentials authorizationCodeCredentials) {
        return Token.builder()
                .accessToken(authorizationCodeCredentials.getAccessToken())
                .refreshToken(authorizationCodeCredentials.getRefreshToken())
                .build();
    }
}
