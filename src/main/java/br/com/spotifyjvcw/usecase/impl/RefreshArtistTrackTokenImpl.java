package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.ArtistHistoric;
import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.TrackHistoric;
import br.com.spotifyjvcw.domain.enums.TermType;
import br.com.spotifyjvcw.exception.especific.SaveDataGatewayException;
import br.com.spotifyjvcw.gateway.SaveDataGateway;
import br.com.spotifyjvcw.gateway.spotify.SpotifyCallApi;
import br.com.spotifyjvcw.usecase.RefreshAndSaveToken;
import br.com.spotifyjvcw.usecase.RefreshArtistTrackToken;
import br.com.spotifyjvcw.usecase.converter.ObjectToIdStringConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@EnableAsync
@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshArtistTrackTokenImpl implements RefreshArtistTrackToken {

    private final RefreshAndSaveToken refreshAndSaveToken;
    private final SaveDataGateway saveDataGateway;
    private final ObjectToIdStringConverter converter;

    @Async
    @Override
    public void execute() {
        log.info("Busca por tokens iniciada");
        List<Token> tokens = saveDataGateway.getAllTokens();
        log.info("Quantidade de tokens encontrados: {}", tokens.size());

        for(Token token : tokens)
            refreshOne(token.getClientId());
    }

    private void refreshOne(String clientId){
        Token token = refreshAndSaveToken.execute(clientId);

        if(isNull(token))
            return;

        log.info("Inciado processo de buscar/salvar dados para clienteId: {}", clientId);
        try {
            saveDataGateway.saveArtists(constructArtistHistoric(token.getAccessToken()), clientId);
            saveDataGateway.saveTracks(constructTrackHistoric(token.getAccessToken()), clientId);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new SaveDataGatewayException(e.getMessage(), e.getCause());
        }
        log.info("Refresh realizado com sucesso do clientId: {}", clientId);
    }

    private ArtistHistoric constructArtistHistoric(String accessToken){
        return ArtistHistoric.builder()
                    .date(LocalDate.now())
                    .artistsLong(converter.execute(SpotifyCallApi
                            .getTopArtistsByTerm(TermType.LONG.getDescription(), accessToken)))
                    .artistsMedium(converter.execute(SpotifyCallApi
                            .getTopArtistsByTerm(TermType.MEDIUM.getDescription(), accessToken)))
                    .artistsShort(converter.execute(SpotifyCallApi
                            .getTopArtistsByTerm(TermType.SHORT.getDescription(), accessToken))).build();

    }

    private TrackHistoric constructTrackHistoric(String accessToken){
        return TrackHistoric.builder()
                .date(LocalDate.now())
                .tracksLong(converter.execute(SpotifyCallApi
                        .getTopTracksByTerm(TermType.LONG.getDescription(), accessToken)))
                .tracksMedium(converter.execute(SpotifyCallApi
                        .getTopTracksByTerm(TermType.MEDIUM.getDescription(), accessToken)))
                .tracksShort(converter.execute(SpotifyCallApi
                        .getTopTracksByTerm(TermType.SHORT.getDescription(), accessToken))).build();

    }
}
