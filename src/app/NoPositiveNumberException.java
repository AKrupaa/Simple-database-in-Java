package app;

class NoPositiveNumberException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoPositiveNumberException(Integer value) {
        super("Liczba " + value + " nie jest dodatnią liczbą naturalną");
    }
}