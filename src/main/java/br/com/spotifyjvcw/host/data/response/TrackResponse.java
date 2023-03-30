package br.com.spotifyjvcw.host.data.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackResponse {
    private String id;
    private String name;
    private Integer popularity;
    private ArtistResponse artistResponse;
}
