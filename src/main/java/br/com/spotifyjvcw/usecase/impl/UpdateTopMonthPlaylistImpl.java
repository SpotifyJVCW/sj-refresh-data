package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.enums.TermType;
import br.com.spotifyjvcw.gateway.spotify.SpotifyCallApi;
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

    private final RefreshAndSaveToken refreshAndSaveToken;

    private static final String PLAYLIST_NAME = "Top 50 Tracks of the Month";

    @Override
    public void execute() {
        List<Token> tokens = refreshAndSaveToken.executeAll();
        tokens.forEach(this::updatePlaylist);
    }

    private void updatePlaylist(Token token){
        Track[] topTracksMonth = SpotifyCallApi.getTopTracksByTerm(TermType.SHORT.getDescription(), token.getAccessToken());
        List<String> idTracks = Arrays.stream(topTracksMonth).map(Track::getId).toList();

        PlaylistSimplified[] playlistSimplifieds = SpotifyCallApi.getCurrentPlaylists(token.getAccessToken(), 50, 0);

        Optional<PlaylistSimplified> playlistSimplifiedOptional = Arrays.stream(playlistSimplifieds)
                .filter(playlistActual -> playlistActual.getName().equals(PLAYLIST_NAME))
                .findFirst();

        Optional<String> playlistIdOptional = playlistSimplifiedOptional.map(PlaylistSimplified::getId);
        if (playlistIdOptional.isEmpty()) {
            playlistIdOptional = Optional.of(SpotifyCallApi.createPlaylist(token.getAccessToken(), token.getClientId(), PLAYLIST_NAME).getId());
        }

        String playlistId = playlistIdOptional.get();
        String[] idSTracks = idTracks.stream().map(x -> "spotify:track:" + x).toArray(String[]::new);
        SpotifyCallApi.updatePlaylist(token.getAccessToken(), playlistId, idSTracks);
    }
}
