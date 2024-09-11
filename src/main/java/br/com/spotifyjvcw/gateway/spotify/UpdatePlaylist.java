package br.com.spotifyjvcw.gateway.spotify;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.playlists.ReplacePlaylistsItemsRequest;

import java.io.IOException;

@Slf4j
public class UpdatePlaylist {

    private final ReplacePlaylistsItemsRequest replacePlaylistsItemsRequest;

    public void update() {
        try {
            replacePlaylistsItemsRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
        }
    }

    public UpdatePlaylist(String accessToken, String playlistId, String[] trackIds) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        replacePlaylistsItemsRequest = spotifyApi.replacePlaylistsItems(playlistId, trackIds)
                .setBodyParameter("range_start", 0)
                .setBodyParameter("insert_before", 0)
                .setBodyParameter("range_lengh", trackIds.length).build();
    }
}
