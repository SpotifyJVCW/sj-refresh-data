package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.enums.TermType;
import br.com.spotifyjvcw.gateway.spotify.SpotifyCallApi;
import br.com.spotifyjvcw.usecase.FindPlaylist;
import br.com.spotifyjvcw.usecase.UpdatePlaylist;
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

    private final FindPlaylist findPlaylist;
    private final UpdatePlaylist updatePlaylist;
    private final RefreshAndSaveToken refreshAndSaveToken;
    private static final String PLAYLIST_MONTH_NAME = "Top 50 Tracks of the Month";
    private static final String PLAYLIST_OAT_NAME = "Top 10 Tracks of all time";

    @Override
    public void execute() {
        List<Token> tokens = refreshAndSaveToken.executeAll();
        tokens.forEach(token -> {
            updatePlaylist(token, TermType.SHORT, 50, PLAYLIST_MONTH_NAME);
            updatePlaylist(token, TermType.LONG, 10, PLAYLIST_OAT_NAME);
        });
    }

    private void updatePlaylist(Token token, TermType termType, Integer quantity, String playlistName){
        Track[] topTracks = SpotifyCallApi.getTopTracksByTerm(termType.getDescription(), token.getAccessToken(), quantity);

        Optional<PlaylistSimplified> playlistSimplified = findPlaylist.byName(playlistName, token);
        String playlistId = playlistSimplified.isPresent() ? playlistSimplified.get().getId() :
                SpotifyCallApi.createPlaylist(token.getAccessToken(), token.getClientId(), playlistName).getId();

        updatePlaylist.execute(token, playlistId, Arrays.stream(topTracks).map(Track::getId).toList());
    }
}
