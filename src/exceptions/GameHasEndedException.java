package exceptions;

public class GameHasEndedException extends Throwable {

    public String error;

    public GameHasEndedException(String error) {
        this.error = error;
    }
}
