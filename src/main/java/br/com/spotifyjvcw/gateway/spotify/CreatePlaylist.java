package br.com.spotifyjvcw.gateway.spotify;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;

import java.io.IOException;

@Slf4j
public class CreatePlaylist {

    private final CreatePlaylistRequest createPlaylistRequest;

    public Playlist create() {
        try {
            return createPlaylistRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
            return new Playlist.Builder().build();
        }
    }

    public CreatePlaylist(String accessToken, String userId, String playlistName) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        createPlaylistRequest = spotifyApi.createPlaylist(userId, playlistName).build();
    }
}
