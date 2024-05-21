package exceptions;

public class UserPaidTooLittleException extends Throwable {

    public String error;

    public UserPaidTooLittleException(String error) {
        this.error = error;
    }
}
