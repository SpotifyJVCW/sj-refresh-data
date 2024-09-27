package br.com.spotifyjvcw.host;

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

    private final UpdateTopMonthPlaylist updateTopMonthPlaylist;

    @GetMapping("/top-playlist")
    public ResponseEntity<Void> refreshPlaylist(){
        updateTopMonthPlaylist.execute();
        return ResponseEntity.ok().build();
    }
}
