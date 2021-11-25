package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.ArtistHistoric;
import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.TrackHistoric;
import br.com.spotifyjvcw.domain.enums.TermType;
import br.com.spotifyjvcw.gateway.SaveDataGateway;
import br.com.spotifyjvcw.gateway.SpotifyGateway;
import br.com.spotifyjvcw.usecase.RefreshArtistTrackToken;
import br.com.spotifyjvcw.usecase.converter.ObjectToIdStringConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class RefreshArtistTrackTokenImpl implements RefreshArtistTrackToken {

    private static final String CLIENT_ID = "a";

    private final SaveDataGateway saveDataGateway;
    private final SpotifyGateway spotifyGateway;
    private final ObjectToIdStringConverter converter;

    @Override
    public void execute() {
        Token token = getRefreshAndSaveToken();

        saveDataGateway.saveArtists(constructArtistHistoric(token.getAccessToken()), CLIENT_ID);
        saveDataGateway.saveTracks(constructTrackHistoric(token.getAccessToken()), CLIENT_ID);
    }

    private Token getRefreshAndSaveToken(){
        Token token = saveDataGateway.getToken(CLIENT_ID);
        token = spotifyGateway.refreshToken(CLIENT_ID, token.getRefreshToken());
        saveDataGateway.refreshToken(CLIENT_ID, token);

        return token;
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
