package app;
/*
W podstawowej wersji aplikacji musi byc zrealizowana funkcjonalnosc Select, Insert, Update oraz Delete. 
Baza danych powinna miec mozliwosc tworzenia co najmniej jednej tabeli, wieksza ilosc jest opcjonalna. 
Aplikacja powinna posiadac interfejs w linii polecen umozliwiajacy jej 
wygodna obsluge i obslugiwac przyjazny dla uzytkownika sposob wyjatki rzucane podczas pracy aplikacji.

Przyklad zastosowania przygotowanej biblioteki jest dowolny, np. kartoteka ksiazek, baza kontaktow, baza danych wartosci pomiarowych.

>> Baza informacji o studencie.
// SELECT nazwa wypisz cos
// INSERT odpowiada za dodanie do bazy wszystkich kolumn
// UPDATE odpowiada za zaaktualizowanie tylko jednego wiersza
// DELETE odpowiada za usuniecie calego wiersza

*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Student student = new Student();

        Scanner scanner = new Scanner(System.in);
        student.setIndexNumber();
         
        System.out.println("Hello World");


    }
}