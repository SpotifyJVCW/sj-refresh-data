package br.com.spotifyjvcw.host.converter.impl;

import br.com.spotifyjvcw.host.converter.TrackResponseConverter;
import br.com.spotifyjvcw.host.data.response.ArtistResponse;
import br.com.spotifyjvcw.host.data.response.TrackResponse;
import com.wrapper.spotify.model_objects.specification.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class TrackResponseConverterImpl implements TrackResponseConverter {

    @Override
    public List<TrackResponse> convertToResponse(List<Track> trackList) {
        if (isNull(trackList))
            return new ArrayList<>();
        return trackList.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    private TrackResponse convertToResponse(Track track) {
        if (isNull(track))
            return null;
        return TrackResponse.builder()
                .id(track.getId())
                .name(track.getName())
                .popularity(track.getPopularity())
                .artistResponse(ArtistResponse.builder()
                        .name(track.getArtists()[0].getName())
                        .id(track.getArtists()[0].getId())
                        .build())
                .build();
    }
}
