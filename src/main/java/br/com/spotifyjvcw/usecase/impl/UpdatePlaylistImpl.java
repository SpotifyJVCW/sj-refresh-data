package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.gateway.spotify.SpotifyCallApi;
import br.com.spotifyjvcw.usecase.UpdatePlaylist;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdatePlaylistImpl implements UpdatePlaylist {

    @Override
    public void execute(Token token, String playlistId, List<String> trackIds) {
        String[] idSTracks = trackIds.stream().map(x -> "spotify:track:" + x).toArray(String[]::new);
        SpotifyCallApi.updatePlaylist(token.getAccessToken(), playlistId, idSTracks);
    }
}
