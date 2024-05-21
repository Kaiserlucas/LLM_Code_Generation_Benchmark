package exceptions;

public class TurnOrderViolationException extends Throwable {

    public String error;

    public TurnOrderViolationException(String error) {
        this.error = error;
    }
}
