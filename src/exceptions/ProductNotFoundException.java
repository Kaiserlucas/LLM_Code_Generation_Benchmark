package exceptions;

public class ProductNotFoundException extends Throwable {

    public String error;

    public ProductNotFoundException(String error) {
        this.error = error;
    }
}
