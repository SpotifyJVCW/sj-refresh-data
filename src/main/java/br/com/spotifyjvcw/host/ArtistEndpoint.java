package br.com.spotifyjvcw.host;

import br.com.spotifyjvcw.host.converter.ArtistResponseConverter;
import br.com.spotifyjvcw.host.data.response.ArtistResponse;
import br.com.spotifyjvcw.usecase.FindArtistsById;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistEndpoint {

    private final FindArtistsById findArtistsById;
    private final ArtistResponseConverter artistResponseConverter;

    @PostMapping("/{clientId}")
    public List<ArtistResponse> getArtists(@PathVariable String clientId,
                                           @Valid @NotNull @RequestBody List<String> artistIdList){
        return artistResponseConverter.convertToResponse(findArtistsById.execute(artistIdList, clientId));
    }
}
