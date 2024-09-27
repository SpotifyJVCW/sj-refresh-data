package br.com.spotifyjvcw.usecase.input;

public record GenerateTokenInput(
        String code,
        String clientId,
        String codeVerifier,
        String redirectUri
) {
}
