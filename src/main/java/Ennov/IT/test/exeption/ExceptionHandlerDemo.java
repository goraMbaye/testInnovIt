package Ennov.IT.test.exeption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandlerDemo extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> userNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(ErrorDto.builder()
                .httpCode(NOT_FOUND.value())
                .message(ex.getMessage())
                .build(), NOT_FOUND);
    }
    @ExceptionHandler(TicketNoTFoundException.class)
    public ResponseEntity<ErrorDto> TicketNotFound(TicketNoTFoundException ex) {
        return new ResponseEntity<>(ErrorDto.builder()
                .httpCode(NOT_FOUND.value())
                .message(ex.getMessage())
                .build(), NOT_FOUND);
    }
}
