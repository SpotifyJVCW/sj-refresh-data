package br.com.spotifyjvcw.gateway.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.enums.TermType;
import br.com.spotifyjvcw.gateway.SpotifyGateway;
import br.com.spotifyjvcw.gateway.converter.TokenEntityToTokenDomainConverter;
import br.com.spotifyjvcw.gateway.spotify.*;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpotifyGatewayImpl implements SpotifyGateway {

    @Value("${spotify.clientIdApplication}")
    private String clientIdApplication;

    private final TokenEntityToTokenDomainConverter entityToTokenDomainConverter;
    private static final String PLAYLIST_NAME = "Top 50 Tracks of the Month";

    @Override
    public void updateMonthPlaylist(String accessToken, String clientId, List<String> idTracks) {
        CreatePlaylist createPlaylist = new CreatePlaylist(accessToken, clientId, PLAYLIST_NAME);
        GetCurrentPlaylists currentPlaylists = new GetCurrentPlaylists(accessToken);
        PlaylistSimplified[] playlistSimplifieds = currentPlaylists.get();

        Optional<PlaylistSimplified> playlistSimplifiedOptional = Arrays.stream(playlistSimplifieds)
                .filter(playlistActual -> playlistActual.getName().equals(PLAYLIST_NAME))
                .findFirst();

        Optional<String> playlistIdOptional = playlistSimplifiedOptional.map(PlaylistSimplified::getId);
        if (playlistIdOptional.isEmpty()) {
            playlistIdOptional = Optional.of(createPlaylist.create().getId());
        }

        String playlistId = playlistIdOptional.get();
        idTracks = idTracks.stream().map(x -> "spotify:track:" + x).toList();
        UpdatePlaylist updatePlaylist = new UpdatePlaylist(accessToken, playlistId, idTracks.toArray(new String[0]));
        updatePlaylist.update();
    }

    @Override
    public Artist[] getTopArtistsByTerm(TermType term, String accessToken) {
        GetUserTopArtists topArtists = new GetUserTopArtists(term.getDescription(), accessToken);
        return topArtists.getUsersTopArtists();
    }

    @Override
    public Track[] getTopTracksByTerm(TermType term, String accessToken) {
        GetUserTopTracks topTracks = new GetUserTopTracks(term.getDescription(), accessToken);
        return topTracks.getUsersTopTracks();
    }

    @Override
    public Artist[] getSeveralArtistsById(List<String> idList, String accessToken) {
        GetSeveralArtists getSeveralArtists = new GetSeveralArtists(idList.toArray(new String[0]), accessToken);
        return getSeveralArtists.getUsersTopArtists();
    }

    @Override
    public Track[] getSeveralTracksById(List<String> idList, String accessToken) {
        GetSeveralTracks getSeveralTracks = new GetSeveralTracks(idList.toArray(new String[0]), accessToken);
        return getSeveralTracks.getUsersTopTracks();
    }

    @Override
    public Token refreshToken(String refreshToken) {
        RefreshToken refreshTokenService = new RefreshToken(clientIdApplication, refreshToken);

        return entityToTokenDomainConverter.execute(refreshTokenService.generateValidToken());
    }
}
