package br.com.spotifyjvcw.gateway.savedata;

import br.com.spotifyjvcw.domain.ArtistHistoric;
import br.com.spotifyjvcw.domain.TrackHistoric;
import br.com.spotifyjvcw.gateway.entity.TokenEntity;

import java.util.List;

public interface SaveDataConnectionApi {

    void saveArtist(List<ArtistHistoric> artistsHistoric, String clientId);
    void saveTrack(List<TrackHistoric> tracksHistoric, String clientId);
    void saveToken(String clientId, String refreshToken);

    TokenEntity findTokenByClientId(String clientId);
    List<TokenEntity> findAllTokens();
}
