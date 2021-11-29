package br.com.spotifyjvcw.exception.especific;

public class FailRefreshTokenException extends RuntimeException{
    public FailRefreshTokenException(String message, Throwable throwable){
        super(message, throwable);
    }

    public FailRefreshTokenException(String message){
        super(message);
    }
}
