package exceptions;

public class ParameterOutOfAllowedBoundsException extends Throwable {

    public String error;

    public ParameterOutOfAllowedBoundsException(String error) {
        this.error = error;
    }
}
