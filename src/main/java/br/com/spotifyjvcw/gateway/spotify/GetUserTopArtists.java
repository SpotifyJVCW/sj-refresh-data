package br.com.spotifyjvcw.gateway.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

@Slf4j
public class GetUserTopArtists {

    private final GetUsersTopArtistsRequest getUsersTopArtistsRequest;

    public Artist[] getUsersTopArtists() {
        try {
            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();

            return artistPaging.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
            return new Artist[0];
        }
    }

    public GetUserTopArtists(String term, String accessToken) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists().limit(50).time_range(term).build();
    }
}
