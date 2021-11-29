package br.com.spotifyjvcw.gateway.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.enums.TermType;
import br.com.spotifyjvcw.gateway.SpotifyGateway;
import br.com.spotifyjvcw.gateway.converter.TokenEntityToTokenDomainConverter;
import br.com.spotifyjvcw.gateway.spotify.GetUserTopArtists;
import br.com.spotifyjvcw.gateway.spotify.GetUserTopTracks;
import br.com.spotifyjvcw.gateway.spotify.RefreshToken;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotifyGatewayImpl implements SpotifyGateway {

    private final TokenEntityToTokenDomainConverter entityToTokenDomainConverter;

    @Override
    public Artist[] getTopArtistsByTerm(TermType term, String accessToken) {
        GetUserTopArtists topArtists = new GetUserTopArtists(term.getDescription(), accessToken);
        return topArtists.getUsersTopArtists();
    }

    @Override
    public Track[] getTopTracksByTerm(TermType term, String accessToken) {
        GetUserTopTracks topTracks = new GetUserTopTracks(term.getDescription(), accessToken);
        return topTracks.getUsersTopTracks();
    }

    @Override
    public Token refreshToken(String clientId, String refreshToken) {
        RefreshToken refreshTokenService = new RefreshToken(clientId, refreshToken);

        return entityToTokenDomainConverter.execute(refreshTokenService.generateValidToken());
    }
}
