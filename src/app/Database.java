package app;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

// >> Baza informacji o studencie.
// SELECT nazwa wypisz cos
// INSERT odpowiada za dodanie do bazy wszystkich kolumn
// UPDATE odpowiada za zaaktualizowanie tylko jednego wiersza
// DELETE odpowiada za usuniecie calego wiersza

public class Database {
    private Map<Integer, Student> students = new TreeMap<>();

    final Scanner scanner = new Scanner(System.in);
    


    public void Select() {
 
    }

    public Map<Integer, Student> Add(TreeMap<Integer, Student> map) {
        Student student = new Student();

        System.out.println("Fill up details:");

        System.out.println("Numer indeksu:");
        student.setIndexNumber();


            

        return map;
    }
}