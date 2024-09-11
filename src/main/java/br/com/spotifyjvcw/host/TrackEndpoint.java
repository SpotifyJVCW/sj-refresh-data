package br.com.spotifyjvcw.host;

import br.com.spotifyjvcw.host.converter.TrackResponseConverter;
import br.com.spotifyjvcw.host.data.response.TrackResponse;
import br.com.spotifyjvcw.usecase.FindTracksById;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackEndpoint {

    private final FindTracksById findTracksById;
    private final TrackResponseConverter trackResponseConverter;

    @PostMapping("/{clientId}")
    public List<TrackResponse> getTracks(@PathVariable String clientId,
                                         @Valid @NotNull @RequestBody List<String> trackIdList){
        return trackResponseConverter.convertToResponse(findTracksById.execute(trackIdList, clientId));
    }
}
