package app;

public class StudentNotFoundException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public StudentNotFoundException(Integer indexNumber) {
        super("Student o numerze " + indexNumber + " nie został znaleziony");
    }
}