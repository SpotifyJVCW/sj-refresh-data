package br.com.spotifyjvcw.usecase;

import se.michaelthelin.spotify.model_objects.specification.Artist;

import java.util.List;

public interface FindArtistsById {

    List<Artist> execute(List<String> artistIdList, String clientId);
}
