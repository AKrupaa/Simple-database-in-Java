package app;

class NoPositiveNumberException extends Exception {
    public NoPositiveNumberException(Integer value) { 
        super("Liczba " + value + " nie jest dodatnią liczbą naturalną");
    }
}