package br.com.spotifyjvcw.host.converter;

import br.com.spotifyjvcw.host.data.response.ArtistResponse;
import com.wrapper.spotify.model_objects.specification.Artist;

import java.util.List;

public interface ArtistResponseConverter {

    List<ArtistResponse> convertToResponse(List<Artist> artistList);
}
