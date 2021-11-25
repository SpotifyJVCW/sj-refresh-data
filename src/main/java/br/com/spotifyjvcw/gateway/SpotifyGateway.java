package br.com.spotifyjvcw.gateway;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.enums.TermType;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Track;

public interface SpotifyGateway {

    Artist[] getTopArtistsByTerm(TermType term, String accessToken);
    Track[] getTopTracksByTerm(TermType term, String accessToken);

    Token refreshToken(String clientId, String refreshToken);
}
