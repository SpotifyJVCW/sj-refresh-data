package br.com.spotifyjvcw.usecase.converter;

import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Track;

public interface ObjectToIdStringConverter {

    String[] execute(Artist[] artists);
    String[] execute(Track[] tracks);
}
