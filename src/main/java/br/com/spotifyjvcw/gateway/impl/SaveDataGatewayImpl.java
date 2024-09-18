package br.com.spotifyjvcw.gateway.impl;

import br.com.spotifyjvcw.domain.ArtistHistoric;
import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.TrackHistoric;
import br.com.spotifyjvcw.gateway.SaveDataGateway;
import br.com.spotifyjvcw.gateway.converter.TokenEntityToTokenDomainConverter;
import br.com.spotifyjvcw.gateway.repository.TokenRepository;
import br.com.spotifyjvcw.gateway.repository.entity.TokenEntity;
import br.com.spotifyjvcw.gateway.savedata.SaveDataConnectionApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveDataGatewayImpl implements SaveDataGateway {

    private final TokenRepository tokenRepository;
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
        return tokenRepository.findById(clientId)
                .map(tokenEntityToTokenDomainConverter::execute)
                .orElse(null);
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenEntityToTokenDomainConverter.execute(tokenRepository.findAll());
    }

    @Override
    public void refreshToken(Token token) {
        TokenEntity tokenEntity = tokenRepository.findById(token.getClientId())
                        .orElseThrow(() -> new RuntimeException("Token not found"));
        tokenEntity.setRefreshToken(token.getRefreshToken());
        tokenRepository.save(tokenEntity);
    }

    @Override
    public void createToken(Token token) {
        tokenRepository.save(TokenEntity.builder()
                .clientId(token.getClientId())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build());
    }
}
