package br.com.spotifyjvcw.gateway;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.domain.enums.TermType;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.Artist;

import java.util.List;

public interface SpotifyGateway {

    void updatePlaylist(String accessToken, String playlistId, List<String> idTracks);
    PlaylistSimplified[] getCurrentPlaylists(String accessToken);
    Playlist createPlaylist(String accessToken, String clientId, String playlistName);
    Artist[] getTopArtistsByTerm(TermType term, String accessToken);
    Track[] getTopTracksByTerm(TermType term, String accessToken);
    Artist[] getSeveralArtistsById(List<String> idList, String accessToken);
    Track[] getSeveralTracksById(List<String> idList, String accessToken);
    Token refreshToken(String refreshToken);
}
