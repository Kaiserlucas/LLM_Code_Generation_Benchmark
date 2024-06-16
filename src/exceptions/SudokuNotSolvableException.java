package exceptions;

public class SudokuNotSolvableException extends Throwable {

    public String error;

    public SudokuNotSolvableException(String error) {
        this.error = error;
    }
}
