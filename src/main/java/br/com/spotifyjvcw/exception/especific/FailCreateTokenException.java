package br.com.spotifyjvcw.exception.especific;

public class FailCreateTokenException extends RuntimeException{

    public FailCreateTokenException(String message){
        super(message);
    }
}
