package br.com.spotifyjvcw.gateway.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {

    @Id
    private String clientId;
    private String accessToken;
    private String refreshToken;
}
