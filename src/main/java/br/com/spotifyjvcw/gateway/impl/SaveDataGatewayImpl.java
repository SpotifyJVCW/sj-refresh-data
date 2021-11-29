package br.com.spotifyjvcw.gateway.impl;

import br.com.spotifyjvcw.domain.ArtistHistoric;
import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.TrackHistoric;
import br.com.spotifyjvcw.gateway.SaveDataGateway;
import br.com.spotifyjvcw.gateway.converter.TokenEntityToTokenDomainConverter;
import br.com.spotifyjvcw.gateway.entity.TokenEntity;
import br.com.spotifyjvcw.gateway.savedata.SaveDataConnectionApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class SaveDataGatewayImpl implements SaveDataGateway {

    private final SaveDataConnectionApi connectionApi;
    private final TokenEntityToTokenDomainConverter tokenEntityToTokenDomainConverter;

    @Override
    public void saveArtists(ArtistHistoric artistHistoric, String clientId) {
        connectionApi.saveArtist(List.of(artistHistoric), clientId);
    }

    @Override
    public void saveTracks(TrackHistoric trackHistoric, String clientId) {
        connectionApi.saveTrack(List.of(trackHistoric), clientId);
    }

    @Override
    public Token getToken(String clientId) {
        TokenEntity tokenEntity = connectionApi.findTokenByClientId(clientId);

        if(isNull(tokenEntity))
            return null;

        return tokenEntityToTokenDomainConverter.execute(tokenEntity);
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenEntityToTokenDomainConverter.execute(connectionApi.findAllTokens());
    }

    @Override
    public void refreshToken(String clientId, Token token) {
        connectionApi.saveToken(clientId, token.getRefreshToken());
    }
}
