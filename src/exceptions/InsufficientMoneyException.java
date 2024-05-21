package exceptions;

public class InsufficientMoneyException extends Throwable {

    public String error;

    public InsufficientMoneyException(String error) {
        this.error = error;
    }
}