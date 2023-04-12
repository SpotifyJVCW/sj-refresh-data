package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.exception.especific.SaveDataGatewayException;
import br.com.spotifyjvcw.gateway.SaveDataGateway;
import br.com.spotifyjvcw.gateway.SpotifyGateway;
import br.com.spotifyjvcw.usecase.RefreshAndSaveToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshAndSaveTokenImpl implements RefreshAndSaveToken {

    private final SaveDataGateway saveDataGateway;
    private final SpotifyGateway spotifyGateway;

    @Override
    public Token execute(String clientId) {
        try {
            log.info("Iniciado refresh para clienteId: {}", clientId);
            Token token = saveDataGateway.getToken(clientId);
            token = spotifyGateway.refreshToken(token.getRefreshToken());

            if(isNull(token)){
                log.error("Não foi possível criar um novo refreshToken! (ClientId: {})", clientId);
                return null;
            }

            saveDataGateway.refreshToken(clientId, token);
            log.info("Refresh do clientId {} realizado com sucesso!", clientId);
            return token;
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new SaveDataGatewayException(e.getMessage(), e.getCause());
        }
    }
}
