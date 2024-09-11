package br.com.spotifyjvcw.gateway.spotify;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;

import java.io.IOException;

@Slf4j
public class GetCurrentPlaylists {

    private final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest;

    public PlaylistSimplified[] get() {
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();

            return playlistSimplifiedPaging.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
            return new PlaylistSimplified[0];
        }
    }

    public GetCurrentPlaylists(String accessToken) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        getListOfCurrentUsersPlaylistsRequest = spotifyApi.getListOfCurrentUsersPlaylists().limit(50).offset(0).build();
    }
}
