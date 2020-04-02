package app;

public class WrongAgeException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public WrongAgeException(Integer value) {
        super("Wiek " + value + " jest nie poprawny");
    }
}