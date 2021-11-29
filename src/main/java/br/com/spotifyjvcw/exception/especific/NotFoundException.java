package br.com.spotifyjvcw.exception.especific;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }

    public NotFoundException(String message){
        super(message);
    }
}
