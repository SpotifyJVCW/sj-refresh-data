package br.com.spotifyjvcw.gateway.savedata.impl;

import br.com.spotifyjvcw.domain.ArtistHistoric;
import br.com.spotifyjvcw.domain.TrackHistoric;
import br.com.spotifyjvcw.gateway.entity.TokenEntity;
import br.com.spotifyjvcw.gateway.savedata.SaveDataConnectionApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log
public class SaveDataConnectionApiImpl implements SaveDataConnectionApi {

    @Value("${save-data.url}")
    private String domainUrl;

    @Value("${save-data.path.save-artists}")
    private String saveArtists;

    @Value("${save-data.path.save-tracks}")
    private String saveTracks;

    @Value("${save-data.path.refresh-token}")
    private String refreshToken;

    @Value("${save-data.path.find-token}")
    private String findToken;


    @Override
    public void saveArtist(List<ArtistHistoric> artistsHistoric, String clientId) {
        RestTemplate request = new RestTemplate();
        ResponseEntity<Void> response = request.postForEntity(
                URI.create(domainUrl + saveArtists + "/" + clientId), artistsHistoric, Void.class);

        if(!response.getStatusCode().is2xxSuccessful())
            log.info("Houve um erro para mandar artists");
    }

    @Override
    public void saveTrack(List<TrackHistoric> tracksHistoric, String clientId) {
        RestTemplate request = new RestTemplate();
        ResponseEntity<Void> response = request.postForEntity(
                URI.create(domainUrl + saveTracks + "/" + clientId), tracksHistoric, Void.class);

        if(!response.getStatusCode().is2xxSuccessful())
            log.info("Houve um erro para mandar tracks");
    }

    @Override
    public void saveToken(String clientId, String refreshToken) {
        RestTemplate request = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Void> response = request.postForEntity(
                URI.create(domainUrl + refreshToken + "/" + clientId),
                new HttpEntity<>("{\"refreshToken\": \"" + refreshToken + "\"}", headers),
                Void.class);

        if(!response.getStatusCode().is2xxSuccessful())
            log.info("Houve um erro para mandar token");
    }

    @Override
    public TokenEntity findTokenByClientId(String clientId) {
        RestTemplate request = new RestTemplate();
        ResponseEntity<TokenEntity> response = request
                .getForEntity(URI.create(domainUrl + findToken + "/" + clientId), TokenEntity.class);

        if(!response.getStatusCode().is2xxSuccessful()){
            log.info("Houve um erro ao receber token");
            return null;
        }

        return response.getBody();
    }
}
