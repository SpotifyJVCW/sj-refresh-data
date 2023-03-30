package br.com.spotifyjvcw.host.converter;

import br.com.spotifyjvcw.host.data.response.TrackResponse;
import com.wrapper.spotify.model_objects.specification.Track;

import java.util.List;

public interface TrackResponseConverter {

    List<TrackResponse> convertToResponse(List<Track> trackList);
}
