package br.com.spotifyjvcw.usecase.converter.impl;

import br.com.spotifyjvcw.usecase.converter.ObjectToIdStringConverter;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import org.springframework.stereotype.Component;

@Component
public class ObjectToIdStringConverterImpl implements ObjectToIdStringConverter {
    @Override
    public String[] execute(Artist[] artists) {
        StringBuilder ids = new StringBuilder();

        for (Artist artist : artists) ids.append(artist.getId()).append(";");

        return stringToArrayId(ids.toString());
    }

    @Override
    public String[] execute(Track[] tracks) {
        StringBuilder ids = new StringBuilder();

        for (Track track : tracks) ids.append(track.getId()).append(";");

        return stringToArrayId(ids.toString());
    }

    private String[] stringToArrayId(String ids) {
        return ids.split(";");
    }
}
