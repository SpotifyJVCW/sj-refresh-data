package br.com.spotifyjvcw.gateway.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

@Slf4j
public class GetUserTopTracks {

    private final GetUsersTopTracksRequest getUsersTopTracksRequest;

    public Track[] getUsersTopTracks() {
        try {
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();

            return trackPaging.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
            return new Track[0];
        }
    }

    public GetUserTopTracks(String term, String accessToken) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        getUsersTopTracksRequest = spotifyApi.getUsersTopTracks().limit(50).time_range(term).build();
    }
}
