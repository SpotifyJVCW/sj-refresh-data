package br.com.spotifyjvcw.gateway;

import br.com.spotifyjvcw.domain.ArtistHistoric;
import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.TrackHistoric;

public interface SaveDataGateway {

    void saveArtists(ArtistHistoric artistHistoric, String clientId);
    void saveTracks(TrackHistoric trackHistoric, String clientId);

    Token getToken(String clientId);
    void refreshToken(String clientId, Token token);
}
