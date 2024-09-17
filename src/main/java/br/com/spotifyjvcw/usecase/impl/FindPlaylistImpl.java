package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.gateway.spotify.SpotifyCallApi;
import br.com.spotifyjvcw.usecase.FindPlaylist;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.Arrays;
import java.util.Optional;

@Component
public class FindPlaylistImpl implements FindPlaylist {
    @Override
    public Optional<PlaylistSimplified> byName(String name, Token token) {
        PlaylistSimplified[] playlistSimplifieds = SpotifyCallApi.getCurrentPlaylists(token.getAccessToken(), 50, 0);

        return Arrays.stream(playlistSimplifieds)
                .filter(playlistActual -> playlistActual.getName().equals(name))
                .findFirst();
    }
}
