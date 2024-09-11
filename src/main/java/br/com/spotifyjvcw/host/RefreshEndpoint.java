package br.com.spotifyjvcw.host;

import br.com.spotifyjvcw.usecase.RefreshArtistTrackToken;
import br.com.spotifyjvcw.usecase.UpdateTopMonthPlaylist;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/refresh")
@RequiredArgsConstructor
public class RefreshEndpoint {

    private final RefreshArtistTrackToken refreshArtistTrackToken;
    private final UpdateTopMonthPlaylist updateTopMonthPlaylist;

    @GetMapping
    public ResponseEntity<Void> refreshData(){
        refreshArtistTrackToken.execute();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top-playlist")
    public ResponseEntity<Void> refreshPlaylist(){
        updateTopMonthPlaylist.execute();
        return ResponseEntity.ok().build();
    }
}
