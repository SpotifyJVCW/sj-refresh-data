package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.ArtistHistoric;
import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.TrackHistoric;
import br.com.spotifyjvcw.domain.enums.TermType;
import br.com.spotifyjvcw.exception.especific.SaveDataGatewayException;
import br.com.spotifyjvcw.gateway.SaveDataGateway;
import br.com.spotifyjvcw.gateway.SpotifyGateway;
import br.com.spotifyjvcw.usecase.RefreshArtistTrackToken;
import br.com.spotifyjvcw.usecase.converter.ObjectToIdStringConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@EnableAsync
@Component
@RequiredArgsConstructor
@Log
public class RefreshArtistTrackTokenImpl implements RefreshArtistTrackToken {

    private final SaveDataGateway saveDataGateway;
    private final SpotifyGateway spotifyGateway;
    private final ObjectToIdStringConverter converter;

    @Async
    @Override
    public void execute() {
        List<Token> tokens = saveDataGateway.getAllTokens();

        for(Token token : tokens)
            refreshOne(token.getClientId());
    }

    private void refreshOne(String clientId){
        Token token = getRefreshAndSaveToken(clientId);

        if(isNull(token))
            return;

        try {
            saveDataGateway.saveArtists(constructArtistHistoric(token.getAccessToken()), clientId);
            saveDataGateway.saveTracks(constructTrackHistoric(token.getAccessToken()), clientId);
        }
        catch (Exception e){
            log.severe(e.getMessage());
            throw new SaveDataGatewayException(e.getMessage(), e.getCause());
        }
        log.info("Refresh realizado com sucesso!  (ClientId: " + clientId + ")");
    }

    private Token getRefreshAndSaveToken(String clientId){
        try {
            Token token = saveDataGateway.getToken(clientId);
            token = spotifyGateway.refreshToken(clientId, token.getRefreshToken());

            if(isNull(token)){
                log.severe("Não foi possível criar um novo refreshToken! (ClientId: " + clientId + ")");
                return null;
            }

            saveDataGateway.refreshToken(clientId, token);
            return token;
        }
        catch (Exception e){
            log.severe(e.getMessage());
            throw new SaveDataGatewayException(e.getMessage(), e.getCause());
        }
    }

    private ArtistHistoric constructArtistHistoric(String accessToken){
        return ArtistHistoric.builder()
                    .date(LocalDate.now())
                    .artistsLong(converter.execute(spotifyGateway
                            .getTopArtistsByTerm(TermType.LONG, accessToken)))
                    .artistsMedium(converter.execute(spotifyGateway
                            .getTopArtistsByTerm(TermType.MEDIUM, accessToken)))
                    .artistsShort(converter.execute(spotifyGateway
                            .getTopArtistsByTerm(TermType.SHORT, accessToken))).build();

    }

    private TrackHistoric constructTrackHistoric(String accessToken){
        return TrackHistoric.builder()
                .date(LocalDate.now())
                .tracksLong(converter.execute(spotifyGateway
                        .getTopTracksByTerm(TermType.LONG, accessToken)))
                .tracksMedium(converter.execute(spotifyGateway
                        .getTopTracksByTerm(TermType.MEDIUM, accessToken)))
                .tracksShort(converter.execute(spotifyGateway
                        .getTopTracksByTerm(TermType.SHORT, accessToken))).build();

    }
}
