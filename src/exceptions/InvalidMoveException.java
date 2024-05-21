package exceptions;

public class InvalidMoveException extends Throwable {

    public String error;

    public InvalidMoveException(String error) {
        this.error = error;
    }
}
