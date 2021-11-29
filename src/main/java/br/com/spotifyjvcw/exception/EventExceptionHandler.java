package br.com.spotifyjvcw.exception;

import br.com.spotifyjvcw.exception.especific.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class EventExceptionHandler extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        String userMessage = "Mensagem inválida!";
        String devMessage = ex.getCause() == null ? null : ex.getCause().toString();

        List<Error> errors = List.of(new Error(userMessage, devMessage));

        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                     WebRequest request) {

        String userMessage = ex.getMessage();
        String devMessage = ex.getCause() == null ? null : ex.getCause().toString();
        List<Error> errors = List.of(new Error(userMessage, devMessage));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex,
                                                                     WebRequest request) {

        String userMessage = ex.getMessage();
        String devMessage = ex.getCause() == null ? null : ex.getCause().toString();
        List<Error> errors = List.of(new Error(userMessage, devMessage));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({InternalServerErrorException.class})
    public ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException ex,
                                                                     WebRequest request) {
        String userMessage = ex.getMessage();
        String devMessage = ex.getCause() == null ? null : ex.getCause().toString();
        List<Error> errors = List.of(new Error(userMessage, devMessage));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({SaveDataGatewayException.class})
    public ResponseEntity<Object> handleGatewayException(SaveDataGatewayException ex,
                                                                     WebRequest request) {
        String userMessage = ex.getMessage();
        String devMessage = ex.getCause() == null ? null : ex.getCause().toString();
        List<Error> errors = List.of(new Error(userMessage, devMessage));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);
    }

    @ExceptionHandler({FailRefreshTokenException.class})
    public ResponseEntity<Object> handleFailRefreshTokenException(FailRefreshTokenException ex,
                                                         WebRequest request) {
        String userMessage = ex.getMessage();
        String devMessage = ex.getCause() == null ? null : ex.getCause().toString();
        List<Error> errors = List.of(new Error(userMessage, devMessage));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Error> errors = createErrorsList(ex.getBindingResult());

        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }


    private List<Error> createErrorsList(BindingResult bindingResult) {
        List<Error> errors = new ArrayList<>();

        for(FieldError fError : bindingResult.getFieldErrors()){
            String userMessage = fError.getDefaultMessage();
            String devMessage = fError.toString();
            errors.add(new Error(userMessage, devMessage));
        }
        return errors;
    }

    public static class Error{

        private String userMessage;
        private String devMessage;

        public Error(String userMessage, String devMessage) {
            this.userMessage = userMessage;
            this.devMessage = devMessage;
        }

        public String getUserMessage() {
            return userMessage;
        }
        public void setUserMessage(String userMessage) {
            this.userMessage = userMessage;
        }
        public String getDevMessage() {
            return devMessage;
        }
        public void setDevMessage(String devMessage) {
            this.devMessage = devMessage;
        }
    }
}
