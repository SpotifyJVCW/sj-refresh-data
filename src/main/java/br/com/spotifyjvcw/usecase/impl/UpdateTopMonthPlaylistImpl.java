package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.enums.TermType;
import br.com.spotifyjvcw.gateway.SpotifyGateway;
import br.com.spotifyjvcw.usecase.UpdateTopMonthPlaylist;
import br.com.spotifyjvcw.usecase.RefreshAndSaveToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateTopMonthPlaylistImpl implements UpdateTopMonthPlaylist {

    private final SpotifyGateway spotifyGateway;
    private final RefreshAndSaveToken refreshAndSaveToken;

    private static final String PLAYLIST_NAME = "Top 50 Tracks of the Month";

    @Override
    public void execute() {
        List<Token> tokens = refreshAndSaveToken.executeAll();
        tokens.forEach(this::updatePlaylist);
    }

    private void updatePlaylist(Token token){
        Track[] topTracksMonth = spotifyGateway.getTopTracksByTerm(TermType.SHORT, token.getAccessToken());
        List<String> idTracks = Arrays.stream(topTracksMonth).map(Track::getId).toList();

        PlaylistSimplified[] playlistSimplifieds = spotifyGateway.getCurrentPlaylists(token.getAccessToken());

        Optional<PlaylistSimplified> playlistSimplifiedOptional = Arrays.stream(playlistSimplifieds)
                .filter(playlistActual -> playlistActual.getName().equals(PLAYLIST_NAME))
                .findFirst();

        Optional<String> playlistIdOptional = playlistSimplifiedOptional.map(PlaylistSimplified::getId);
        if (playlistIdOptional.isEmpty()) {
            playlistIdOptional = Optional.of(spotifyGateway.createPlaylist(token.getAccessToken(), token.getClientId(), PLAYLIST_NAME).getId());
        }

        String playlistId = playlistIdOptional.get();
        idTracks = idTracks.stream().map(x -> "spotify:track:" + x).toList();
        spotifyGateway.updatePlaylist(token.getAccessToken(), playlistId, idTracks);
    }
}
