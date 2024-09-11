package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.enums.TermType;
import br.com.spotifyjvcw.gateway.SpotifyGateway;
import br.com.spotifyjvcw.usecase.UpdateTopMonthPlaylist;
import br.com.spotifyjvcw.usecase.RefreshAndSaveToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateTopMonthPlaylistImpl implements UpdateTopMonthPlaylist {

    private final SpotifyGateway spotifyGateway;
    private final RefreshAndSaveToken refreshAndSaveToken;
    @Override
    public void execute() {
        List<Token> tokens = refreshAndSaveToken.executeAll();
        tokens.forEach(this::updatePlaylist);
    }

    private void updatePlaylist(Token token){
        Track[] topTracksMonth = spotifyGateway.getTopTracksByTerm(TermType.SHORT, token.getAccessToken());
        spotifyGateway.updateMonthPlaylist(token.getAccessToken(), token.getClientId(),
                Arrays.stream(topTracksMonth).map(Track::getId).toList());
    }
}
