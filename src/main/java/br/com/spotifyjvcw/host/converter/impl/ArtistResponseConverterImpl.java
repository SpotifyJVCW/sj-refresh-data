package br.com.spotifyjvcw.host.converter.impl;

import br.com.spotifyjvcw.host.converter.ArtistResponseConverter;
import br.com.spotifyjvcw.host.data.response.ArtistResponse;
import com.wrapper.spotify.model_objects.specification.Artist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class ArtistResponseConverterImpl implements ArtistResponseConverter {

    @Override
    public List<ArtistResponse> convertToResponse(List<Artist> artistList) {
        if (isNull(artistList))
            return new ArrayList<>();

        return artistList.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    private ArtistResponse convertToResponse(Artist artist) {
        if (isNull(artist))
            return null;

        return ArtistResponse.builder()
                .id(artist.getId())
                .name(artist.getName())
                .popularity(artist.getPopularity())
                .genres(artist.getGenres())
                .build();
    }
}
