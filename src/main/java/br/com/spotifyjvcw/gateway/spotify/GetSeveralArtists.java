package br.com.spotifyjvcw.gateway.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.requests.data.artists.GetSeveralArtistsRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

@Slf4j
public class GetSeveralArtists {

    private final GetSeveralArtistsRequest getSeveralArtistsRequest;

    public Artist[] getUsersTopArtists() {
        try {
            return getSeveralArtistsRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
            return new Artist[0];
        }
    }

    public GetSeveralArtists(String[] ids, String accessToken) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        getSeveralArtistsRequest = spotifyApi.getSeveralArtists(ids).build();
    }
}
