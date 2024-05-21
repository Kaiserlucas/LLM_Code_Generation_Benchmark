package exceptions;

public class OutOfStockException extends Throwable {

    public String error;

    public OutOfStockException(String error) {
        this.error = error;
    }

}