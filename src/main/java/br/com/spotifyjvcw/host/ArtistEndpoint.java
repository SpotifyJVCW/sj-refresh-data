package br.com.spotifyjvcw.host;

import br.com.spotifyjvcw.exception.EventExceptionHandler;
import br.com.spotifyjvcw.host.converter.ArtistResponseConverter;
import br.com.spotifyjvcw.host.data.response.ArtistResponse;
import br.com.spotifyjvcw.usecase.FindArtistsById;
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
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistEndpoint {

    private final FindArtistsById findArtistsById;
    private final ArtistResponseConverter artistResponseConverter;

    @Operation(tags = "Artists", description = "Busca Artists pelo Id", responses = {
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
    public List<ArtistResponse> getArtists(@PathVariable String clientId,
                                           @Valid @NotNull @RequestBody List<String> artistIdList){
        return artistResponseConverter.convertToResponse(findArtistsById.execute(artistIdList, clientId));
    }
}
