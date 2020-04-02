package app;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

// >> Baza informacji o studencie.
// SELECT nazwa wypisz cos
// INSERT odpowiada za dodanie do bazy wszystkich kolumn
// UPDATE odpowiada za zaaktualizowanie tylko jednego wiersza
// DELETE odpowiada za usuniecie calego wiersza

public class Database {
    Map<Integer, Student> studensMap;
    final Scanner scanner = new Scanner(System.in);

    public Database() {
        studensMap = new TreeMap<>();
    }

    public void Select() {

    }

    public Map<Integer, Student> Insert() {
        Student student = new Student();

        System.out.println("Wypełnij dane:");

        System.out.println("Wprowadz numer indeksu:");
        student.setIndexNumber();
        System.out.println("Wprwadź imię studenta:");
        student.setFirstName();
        System.out.println("Wprwadź nazwisko studenta:");
        student.setSurName();
        System.out.println("Wprowadz adres studenta");
        student.setAddress();
        System.out.println("Wprowadz wiek studenta");
        student.setAge();
        System.out.println("Wprowadz płeć studenta");
        student.setGender(); // true - male <-> false - female
        System.out.println("Dane zweryfikowane!");

        this.studensMap.put(student.getIndexNumber(), student);

        return this.studensMap;
    }

    public void Update() {

        Integer inputedIndexNumber;
        Student student = new Student();

        System.out.println("Podaj numer indeksu studenta aby edytować jego dane.");
        // od razu korzystam z stworzonych funkcji weryfikujacych poprawnosc wyrazenia
        student.setIndexNumber();
        // poprawne wyrazenie okreslajace indeks
        inputedIndexNumber = student.getIndexNumber();

        // wyszukiwanie takiego studenta w bazie danych
        Set<Integer> studentsKeys = this.studensMap.keySet();
        for (Integer studentKey : studentsKeys) {
            System.out.println(studentKey);

            // jezeli znajde taki klucz w mojej bazie danych rowny numerowi indeksu to:
            if (studentKey == inputedIndexNumber) {
                // student jest przypisywany do zawartosci z bazdy danych
                student = studensMap.get(inputedIndexNumber);
                System.out.println("Znaleziono studenta!");
                System.out.println("Wybierz, którą ");

                // zakladam ze zmieniam jego imie
                // student.setFirstName();

                // // student jest wkladany na miejsce starego studenta;
                // this.studensMap.put(inputedIndexNumber, student);

                // // wypisuje studenta aby sprawdzic poprawnosc danych

                // System.out.println(student.getFirstName());
            }

        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        // wypiszmy kolekcje by zobaczyc czy rzeczywiscie zostala podmieniona
        Set<Entry<Integer, Student>> entrySet = studensMap.entrySet();
        for (Entry<Integer, Student> entry : entrySet) {
            System.out.println(entry.getKey().toString() + " : " + entry.getValue().getFirstName());
        }
    }
}