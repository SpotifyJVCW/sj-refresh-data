package br.com.spotifyjvcw.gateway.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.tracks.GetSeveralTracksRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

@Slf4j
public class GetSeveralTracks {

    private final GetSeveralTracksRequest getSeveralTracksRequest;

    public Track[] getUsersTopTracks() {
        try {
            return getSeveralTracksRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
            return new Track[0];
        }
    }

    public GetSeveralTracks(String[] ids, String accessToken) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        getSeveralTracksRequest = spotifyApi.getSeveralTracks(ids).build();
    }
}
