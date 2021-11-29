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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log
public class SaveDataConnectionApiImpl implements SaveDataConnectionApi {

    @Value("${save-data.url}")
    private String domainUrl;

    @Value("${save-data.path.save-artists}")
    private String saveArtistsPath;

    @Value("${save-data.path.save-tracks}")
    private String saveTracksPath;

    @Value("${save-data.path.refresh-token}")
    private String refreshTokenPath;

    @Value("${save-data.path.find-token}")
    private String findTokenPath;


    @Override
    public void saveArtist(List<ArtistHistoric> artistsHistoric, String clientId) {
        RestTemplate request = new RestTemplate();
        ResponseEntity<Void> response = request.postForEntity(
                URI.create(domainUrl + saveArtistsPath + "/" + clientId), artistsHistoric, Void.class);

        if(!response.getStatusCode().is2xxSuccessful())
            log.info("Houve um erro para mandar artists");
    }

    @Override
    public void saveTrack(List<TrackHistoric> tracksHistoric, String clientId) {
        RestTemplate request = new RestTemplate();
        ResponseEntity<Void> response = request.postForEntity(
                URI.create(domainUrl + saveTracksPath + "/" + clientId), tracksHistoric, Void.class);

        if(!response.getStatusCode().is2xxSuccessful())
            log.info("Houve um erro para mandar tracks");
    }

    @Override
    public void saveToken(String clientId, String refreshToken) {
        RestTemplate request = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Void> response = request.postForEntity(
                URI.create(domainUrl + "/" + refreshTokenPath + "/" + clientId),
                new HttpEntity<>("{\"refreshToken\": \"" + refreshToken + "\"}", headers),
                Void.class);

        if(!response.getStatusCode().is2xxSuccessful())
            log.info("Houve um erro para mandar token");
    }

    @Override
    public TokenEntity findTokenByClientId(String clientId) {
        RestTemplate request = new RestTemplate();
        ResponseEntity<TokenEntity> response = request
                .getForEntity(URI.create(domainUrl + findTokenPath + "/" + clientId), TokenEntity.class);

        if(!response.getStatusCode().is2xxSuccessful()){
            log.info("Houve um erro ao receber token");
            return null;
        }

        return response.getBody();
    }

    @Override
    public List<TokenEntity> findAllTokens() {
        RestTemplate request = new RestTemplate();
        ResponseEntity<TokenEntity[]> response = request
                .getForEntity(URI.create(domainUrl + findTokenPath), TokenEntity[].class);

        if(!response.getStatusCode().is2xxSuccessful()){
            log.info("Houve um erro ao receber token");
            return new ArrayList<>();
        }

        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
