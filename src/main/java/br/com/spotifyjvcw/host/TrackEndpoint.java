package br.com.spotifyjvcw.host;

import br.com.spotifyjvcw.exception.EventExceptionHandler;
import br.com.spotifyjvcw.host.converter.TrackResponseConverter;
import br.com.spotifyjvcw.host.data.response.ArtistResponse;
import br.com.spotifyjvcw.host.data.response.TrackResponse;
import br.com.spotifyjvcw.usecase.FindTracksById;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackEndpoint {

    private final FindTracksById findTracksById;
    private final TrackResponseConverter trackResponseConverter;

    @Operation(tags = "Tracks", description = "Busca Tracks pelo Id", responses = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content( mediaType = "application/json",
                    array = @ArraySchema( schema = @Schema(implementation = ArtistResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventExceptionHandler.Error.class))),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventExceptionHandler.Error.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventExceptionHandler.Error.class)))
    })
    @PostMapping("/{clientId}")
    public List<TrackResponse> getTracks(@PathVariable String clientId,
                                         @Valid @NotNull @RequestBody List<String> trackIdList){
        return trackResponseConverter.convertToResponse(findTracksById.execute(trackIdList, clientId));
    }
}
