package br.com.spotifyjvcw.usecase;

import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.List;

public interface FindTracksById {

    List<Track> execute(List<String> trackIdList, String clientId);
}
