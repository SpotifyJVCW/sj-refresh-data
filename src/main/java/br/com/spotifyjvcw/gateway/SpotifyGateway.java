package br.com.spotifyjvcw.gateway;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.enums.TermType;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Track;

import java.util.List;

public interface SpotifyGateway {

    Artist[] getTopArtistsByTerm(TermType term, String accessToken);
    Track[] getTopTracksByTerm(TermType term, String accessToken);
    Artist[] getSeveralArtistsById(List<String> idList, String accessToken);
    Track[] getSeveralTracksById(List<String> idList, String accessToken);
    Token refreshToken(String refreshToken);
}
