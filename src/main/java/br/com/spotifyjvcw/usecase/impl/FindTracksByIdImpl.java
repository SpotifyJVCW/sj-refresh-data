package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.gateway.spotify.SpotifyCallApi;
import br.com.spotifyjvcw.usecase.FindTracksById;
import br.com.spotifyjvcw.usecase.RefreshAndSaveToken;
import se.michaelthelin.spotify.model_objects.specification.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindTracksByIdImpl implements FindTracksById {

    private final RefreshAndSaveToken refreshAndSaveToken;
    @Override
    public List<Track> execute(List<String> trackIdList, String clientId) {
        Token token = refreshAndSaveToken.execute(clientId);

        String[] trackIdArray = trackIdList.toArray(new String[0]);
        return List.of(SpotifyCallApi.getSeveralTracksById(trackIdArray, token.getAccessToken()));
    }
}
