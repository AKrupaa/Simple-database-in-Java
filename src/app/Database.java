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
        Student student = new Student("firstName", "surName", "address", 10, Gender.M, 1);
        Student student1 = new Student("firstName", "surName", "address", 10, Gender.M, 2);
        Student student2 = new Student("firstName", "surName", "address", 10, Gender.M, 3);
        Student student3 = new Student("firstName", "surName", "address", 10, Gender.M, 4);
        Student student4 = new Student("firstName", "surName", "address", 10, Gender.M, 5);
        studensMap.put(student.getIndexNumber(), student);
        studensMap.put(student1.getIndexNumber(), student1);
        studensMap.put(student2.getIndexNumber(), student2);
        studensMap.put(student3.getIndexNumber(), student3);
        studensMap.put(student4.getIndexNumber(), student4);
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

    public Map<Integer, Student> Update() {

        Integer inputedIndexNumber;
        Student student = new Student();

        System.out.println("Podaj numer indeksu studenta aby edytować jego dane.");
        // od razu korzystam z stworzonych funkcji weryfikujacych poprawnosc wyrazenia
        student.setIndexNumber();
        // poprawne wyrazenie okreslajace indeks
        inputedIndexNumber = student.getIndexNumber();

        // nie znaleziono
        if (!findStudentByIndexNumber(inputedIndexNumber))
            return this.studensMap;

        System.out.println("Znaleziono studenta!");
        // ~~~~~ znaleziono studenta ~~~~~

        // student jest przypisywany do zawartosci z bazy danych
        student = studensMap.get(inputedIndexNumber);

        // przedstawienie opcji oraz wybranie
        AvailableStudentData availableStudentData = selectingAvailableOption();
        // wykonanie wybranej opcji, czyli jezeli zostal wybrany edytuj indeks ->
        // edytuje indeks
        // zwraca akutalny poprawny indeks
        inputedIndexNumber = doSelectedOption(availableStudentData, inputedIndexNumber, student);

        // Informacje znajdujące się w bazie danych są aktualizowane nowymi wartościami
        this.studensMap.replace(inputedIndexNumber, studensMap.get(inputedIndexNumber), student);

        testowanie(studensMap);

        return this.studensMap;
    }

    private void testowanie(Map<Integer, Student> map) {
        // wypiszmy kolekcje by zobaczyc czy rzeczywiscie zostala podmieniona
        Set<Entry<Integer, Student>> entrySet = map.entrySet();
        for (Entry<Integer, Student> entry : entrySet) {
            System.out.println(entry.getKey().toString() + " : " + entry.getValue().getIndexNumber());
        }
    }

    private Map<Integer, Student> replaceTheKeys(Integer oldKey, Integer newKey, Map<Integer, Student> map) {
        // sprawdz czy istnieje już taki klucz (DUPLIKAT)
        if (isDuplicated(oldKey, newKey))
            return map;

        // nie jest zduplikowany
        // wez wartosc z bazy danych
        // usun caly wierz
        Student value = map.remove(oldKey);
        if (value == null)
            System.out.println("Numer indeksu nie posiada powiązanego z nim informacji o studencie!");

        // wstaw nowy klucz i starą wartość
        map.put(newKey, value);
        // zwroc mape
        return map;
    }

    private boolean findStudentByIndexNumber(Integer index) {
        Set<Integer> studentsKeys = this.studensMap.keySet();

        // wyszukiwanie studenta o podanym numerze indeksu w bazie danych
        for (Integer studentKey : studentsKeys) {
            // jeżeli znajdzie to zwracamy indeks
            // w innym wypadku rzucamy wyjatek i papa
            if (studentKey == index)
                break;
            else {
                // nie znaleziono
                System.out.println("Nie znaleziono studenta o podanym numerze indeksu " + index);
                return false;
            }
        }
        return true;
    }

    private AvailableStudentData selectingAvailableOption() {
        System.out.println("Wybierz opcję, którą chciałbyś edytować: ");
        System.out.println("Wpisz 'imie', aby zmienić imię studenta");
        System.out.println("Wpisz 'nazwisko', aby zmienić nazwisko studenta");
        System.out.println("Wpisz 'adres', aby zmienić adres zamieszkania studenta");
        System.out.println("Wpisz 'wiek', aby zmienić wiek studenta");
        System.out.println("Wpisz 'plec', aby zmienić plec studenta");
        System.out.println("Wpisz 'indeks', aby zmienić indeks studenta");

        // sprawdzam czy dobrze wpisano wartość dot. edycji danych
        AvailableStudentData availableStudentData = AvailableStudentData.unknow;
        try {
            availableStudentData = AvailableStudentData.valueOf(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Wprowadzono niepoprawne dane");
            System.out.println("Spróbuj ponownie!");
        }
        return availableStudentData;
    }

    private Integer doSelectedOption(AvailableStudentData availableStudentData, Integer inputedIndexNumber,
            Student student) {
        switch (availableStudentData) {
            case indeks:
                // ustaw nowy indeks
                student.setIndexNumber();
                // zamien klucze, wartosc pozostaw nie zmieniona
                studensMap = replaceTheKeys(inputedIndexNumber, student.getIndexNumber(), studensMap);
                // podmieniliśmy, ten który był wzorcem -> zamień - nie istniejący student na
                // nic nam się nie przyda
                inputedIndexNumber = student.getIndexNumber();
                break;
            case adres:
                student.setAddress();
                break;
            case imie:
                student.setFirstName();
                break;
            case nazwisko:
                student.setSurName();
                break;
            case plec:
                student.setGender();
                break;
            case wiek:
                student.setAge();
                break;
            case unknow:
            default:
                System.out.print("Niemożliwe, ale jednak coś napiszę. Kontunuowanie...");
                break;
        }

        return inputedIndexNumber;
    }

    private boolean isDuplicated(Integer oldKey, Integer newKey) {
        int counter = 0;
        Set<Integer> studentsKeys = this.studensMap.keySet();

        // przypisanie do siebie samego?
        if (oldKey == newKey) {
            System.out.println("Stary indeks jest taki sam co nowy");
            return true;
        }

        // wyszukiwanie studenta o podanym numerze indeksu w bazie danych
        for (Integer studentKey : studentsKeys) {
            if (studentKey == newKey)
                counter++;
        }

        // jest duplikat
        if (counter > 1)
            return true;

        // nie ma duplikatu
        return false;
    }
}