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
    private static final String PLAYLIST_NAME = "Top 50 Tracks of the Month";

    @Override
    public void execute() {
        List<Token> tokens = refreshAndSaveToken.executeAll();
        tokens.forEach(this::updatePlaylist);
    }

    private void updatePlaylist(Token token){
        Track[] topTracksMonth = SpotifyCallApi.getTopTracksByTerm(TermType.SHORT.getDescription(), token.getAccessToken());

        Optional<PlaylistSimplified> playlistSimplified = findPlaylist.byName(PLAYLIST_NAME, token);
        String playlistId = playlistSimplified.isPresent() ? playlistSimplified.get().getId() :
                SpotifyCallApi.createPlaylist(token.getAccessToken(), token.getClientId(), PLAYLIST_NAME).getId();

        updatePlaylist.execute(token, playlistId, Arrays.stream(topTracksMonth).map(Track::getId).toList());
    }
}
