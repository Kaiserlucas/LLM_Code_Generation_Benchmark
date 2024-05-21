package exceptions;

public class InvalidEncodingException extends Throwable {

    public String error;

    public InvalidEncodingException(String error) {
        this.error = error;
    }
}
