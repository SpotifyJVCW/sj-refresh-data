package br.com.spotifyjvcw.exception.especific;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message, Throwable throwable){
        super(message, throwable);
    }

    public InternalServerErrorException(String message){
        super(message);
    }
}
