package br.com.spotifyjvcw.usecase.impl;

import br.com.spotifyjvcw.domain.Token;
import br.com.spotifyjvcw.exception.especific.SaveDataGatewayException;
import br.com.spotifyjvcw.gateway.SaveDataGateway;
import br.com.spotifyjvcw.gateway.spotify.SpotifyCallApi;
import br.com.spotifyjvcw.usecase.RefreshAndSaveToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshAndSaveTokenImpl implements RefreshAndSaveToken {

    private final SaveDataGateway saveDataGateway;

    @Value("${spotify.clientIdApplication}")
    private String clientIdApplication;

    @Override
    public Token execute(String clientId) {
        return execute(saveDataGateway.getToken(clientId));
    }

    @Override
    public List<Token> executeAll() {
        log.info("Busca por tokens iniciada");
        List<Token> tokens = saveDataGateway.getAllTokens();
        log.info("Quantidade de tokens encontrados: {}", tokens.size());
        return tokens.stream().map(this::execute).toList();
    }

    private Token execute(Token token){
        try {
            if (isNull(token)) {
                log.error("Token não encontrado!");
                return null;
            }

            String clientId = token.getClientId();
            log.info("Iniciado refresh para clienteId: {}", clientId);
            token = SpotifyCallApi.refreshToken(token.getRefreshToken(), clientIdApplication);

            if(isNull(token)){
                log.error("Não foi possível criar um novo refreshToken! (ClientId: {})", clientId);
                return null;
            }
            token.setClientId(clientId);
            saveDataGateway.refreshToken(token);
            log.info("Refresh do clientId {} realizado com sucesso!", clientId);
            return token;
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new SaveDataGatewayException(e.getMessage(), e.getCause());
        }
    }
}
