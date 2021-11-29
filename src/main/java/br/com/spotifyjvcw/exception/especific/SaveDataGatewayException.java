package br.com.spotifyjvcw.exception.especific;

public class SaveDataGatewayException extends RuntimeException{
    public SaveDataGatewayException(String message, Throwable throwable){
        super(message, throwable);
    }

    public SaveDataGatewayException(String message){
        super(message);
    }
}
