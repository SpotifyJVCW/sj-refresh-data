package br.com.spotifyjvcw.gateway.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.enums.TermType;
import br.com.spotifyjvcw.gateway.SpotifyGateway;
import br.com.spotifyjvcw.gateway.converter.TokenEntityToTokenDomainConverter;
import br.com.spotifyjvcw.gateway.spotify.*;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpotifyGatewayImpl implements SpotifyGateway {

    @Value("${spotify.clientIdApplication}")
    private String clientIdApplication;

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
    public Artist[] getSeveralArtistsById(List<String> idList, String accessToken) {
        GetSeveralArtists getSeveralArtists = new GetSeveralArtists(idList.toArray(new String[0]), accessToken);
        return getSeveralArtists.getUsersTopArtists();
    }

    @Override
    public Track[] getSeveralTracksById(List<String> idList, String accessToken) {
        GetSeveralTracks getSeveralTracks = new GetSeveralTracks(idList.toArray(new String[0]), accessToken);
        return getSeveralTracks.getUsersTopTracks();
    }

    @Override
    public Token refreshToken(String refreshToken) {
        RefreshToken refreshTokenService = new RefreshToken(clientIdApplication, refreshToken);

        return entityToTokenDomainConverter.execute(refreshTokenService.generateValidToken());
    }
}
