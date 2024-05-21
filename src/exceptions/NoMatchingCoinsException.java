package exceptions;

public class NoMatchingCoinsException extends Throwable {

    public String error;

    public NoMatchingCoinsException(String error) {
        this.error = error;
    }
}
