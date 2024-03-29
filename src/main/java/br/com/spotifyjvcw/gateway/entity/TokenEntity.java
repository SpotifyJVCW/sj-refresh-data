package br.com.spotifyjvcw.gateway.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenEntity {

    private String clientId;
    private String accessToken;
    private String refreshToken;
}
