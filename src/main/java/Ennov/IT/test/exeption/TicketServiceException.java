package Ennov.IT.test.exeption;

public class TicketServiceException extends RuntimeException {

    public TicketServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
