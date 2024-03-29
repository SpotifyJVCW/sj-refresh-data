package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.gateway.SpotifyGateway;
import br.com.spotifyjvcw.usecase.FindArtistsById;
import br.com.spotifyjvcw.usecase.RefreshAndSaveToken;
import com.wrapper.spotify.model_objects.specification.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindArtistsByIdImpl implements FindArtistsById {

    private final SpotifyGateway spotifyGateway;
    private final RefreshAndSaveToken refreshAndSaveToken;
    @Override
    public List<Artist> execute(List<String> artistIdList, String clientId) {
        Token token = refreshAndSaveToken.execute(clientId);
        return List.of(spotifyGateway.getSeveralArtistsById(artistIdList, token.getAccessToken()));
    }
}
