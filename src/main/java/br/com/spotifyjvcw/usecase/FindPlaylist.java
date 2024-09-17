package br.com.spotifyjvcw.usecase;

import br.com.spotifyjvcw.domain.Token;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.Optional;

public interface FindPlaylist {

    Optional<PlaylistSimplified> byName(String name, Token token);
}
