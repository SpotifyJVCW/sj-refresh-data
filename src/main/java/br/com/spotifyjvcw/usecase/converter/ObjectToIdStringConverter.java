package br.com.spotifyjvcw.usecase.converter;

import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;

public interface ObjectToIdStringConverter {

    String[] execute(Artist[] artists);
    String[] execute(Track[] tracks);
}
