package br.com.spotifyjvcw.usecase;

public interface ValidateAuthentication {

    String createSpotifyUrlNewUser();
    void generateToken(String code);
}
