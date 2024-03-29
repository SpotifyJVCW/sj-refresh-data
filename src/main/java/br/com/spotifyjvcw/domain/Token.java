package br.com.spotifyjvcw.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    private String clientId;
    private String accessToken;
    private String refreshToken;
}
