package app;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ZbiorTabel {
    private Map<String, Tabela> zbiorTabel;

    public ZbiorTabel() {
        this.zbiorTabel = new TreeMap<>();
    }

    public void syntaxHandler(String command) {
        if (command.startsWith("CREATE TABLE ")) {
            try {
                create(command);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else if (command.startsWith("DELETE FROM ")) {
            try {
                delete(command);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (command.startsWith("SELECT ")) {
            try {
                select(command);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (command.startsWith("INSERT INTO ")) {
            try {
                insert(command);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // działa spoko
    public void create(String komenda) throws Exception {
        // CREATE TABLE table_name (
        // column1,
        // column2,
        // column3,
        // ....
        // );

        // SYNTAX wlasciwy + spacja "CREATE_TABLE_"
        komenda = komenda.substring(13);
        char[] zbiorSymboli = komenda.toCharArray();

        boolean wystapilaNazwaTablicy = false;
        boolean wystapilNawiasOtwierajacy = false;
        boolean wystapilNawiasZamykajacy = false;

        ArrayList<String> zbiorNazwKolumn = new ArrayList<String>();
        String nazwaTablicy = "";
        boolean tempZmieniony = false;
        String temp = "";

        // CREATE SYNTAX CHECKER
        for (int i = 0; i < zbiorSymboli.length; i++) {

            if (i == zbiorSymboli.length - 1) {
                if (zbiorSymboli[i] != ';')
                    throw new Exception("Błąd składni, oczekiwano ');'");
                if (wystapilNawiasOtwierajacy == false || wystapilNawiasZamykajacy == false)
                    throw new Exception("Błąd składni, oczekiwano '(...);'");
            }

            // wystapil po raz drugi symbol '('
            if (zbiorSymboli[i] == '(' && wystapilNawiasOtwierajacy == true)
                throw new Exception("Błąd składni, '(' pojawił się dwukrotnie!");

            // wystapil po raz drugi symbol ')'
            if (zbiorSymboli[i] == ')' && wystapilNawiasZamykajacy == true)
                throw new Exception("Błąd składni, ')' pojawił się dwukrotnie!");

            // szukamy nazwy tablicy
            if (wystapilaNazwaTablicy == false) {
                if (zbiorSymboli[i] == '(') {
                    wystapilNawiasOtwierajacy = true;
                    wystapilaNazwaTablicy = true;
                    // jezeli ktos robi sobie zarty
                    if (temp.length() > 0) {
                        nazwaTablicy = temp;
                        temp = "";
                        // nie dodawaj '(' do przestrzeni nazw -> JUZ teraz kolumn!
                        tempZmieniony = true;
                        continue;
                    } else
                        throw new Exception("Wpisz nazwe tablicy!");
                }
                // zbieraj nazwe tablicy do paki
                temp += zbiorSymboli[i];
                continue;
            }

            if (zbiorSymboli[i] == ')') {
                if (temp.length() > 0) {
                    zbiorNazwKolumn.add(temp);
                }
                wystapilNawiasZamykajacy = true;
                if (wystapilNawiasOtwierajacy == false)
                    throw new Exception("Błąd składni, znak '(' nie pojawił się!");
                if (i == zbiorSymboli.length - 1)
                    throw new Exception("Błąd składni, oczekiwano ');'");

                if (zbiorSymboli[i + 1] == ';')
                    // skladnia OK
                    break;
            }

            if (zbiorSymboli[i] == ',') {
                if (temp.length() > 0) {
                    zbiorNazwKolumn.add(temp);
                    temp = "";
                    continue;
                } else
                    throw new Exception("Błąd składni, za dużo przecinków ,,");
            }

            temp += zbiorSymboli[i];
        }
        // CREATE SYNTAX CHECKER
        if (tempZmieniony == false) {
            throw new Exception("Błąd składni!");
        }

        // rozgrywka wygrana!
        temp = "";
        // po wszystkim i tak kolejne sortowanie wynikow
        // te SPACJE mnie zabiją
        nazwaTablicy = nazwaTablicy.replaceAll(" ", "");
        // System.out.println("Nazwa tablicy: " + nazwaTablicy);
        // System.out.println("KOLUMNY: ");
        // for (int i = 0; i < zbiorNazwKolumn.size(); i++) {
        // temp = zbiorNazwKolumn.get(i);
        // temp = temp.replaceAll(" ", "");
        // zbiorNazwKolumn.set(i, temp);
        // System.out.println(temp);
        // }

        // dodaj "sensowne" dane
        Tabela tabela = new Tabela();
        for (String string : zbiorNazwKolumn) {
            tabela.dodajKolumne(string.replaceAll(" ", ""));
        }
        // umiesc i elo
        zbiorTabel.put(nazwaTablicy, tabela);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ działa poprawnie!!!!!
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        // // wypisz sobie dla fanu, przeciez nie debagujesz
        // for (Entry<String, Tabela> entry : zbiorTabel.entrySet()) {
        // System.out.println();
        // System.out.println("Utworzono tabele o nazwie: " +
        // entry.getKey().toString());
        // System.out.println("Kolumny: ");
        // entry.getValue().wypiszWszystkieKolumny();
        // System.out.println();
        // }
    }

    // // działa spoko
    public void delete(String komenda) throws Exception {
        // DELETE FROM table_name WHERE condition;
        // DELETE FROM table_name WHERE column_name == 'Alfreds Futterkiste'
        // DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
        // DELETE FROM table_name;

        // SYNTAX wlasciwy + spacja "DELETE_FROM_"
        komenda = komenda.substring(12);
        char[] zbiorSymboli = komenda.toCharArray();

        boolean wystapilaNazwaTablicy = false;
        boolean wystapilWHERE = false;
        boolean wystapilaZmiana = false;

        String nazwaTablicy = "";
        String warunek = "";
        String temp = "";

        // SYNTAX
        for (int i = 0; i < zbiorSymboli.length; i++) {
            // ; - wyjscie
            if (i == zbiorSymboli.length - 1 || zbiorSymboli[i] == ';') {
                if (zbiorSymboli[i] != ';')
                    throw new Exception("Oczekiwano ';'");
                else {
                    // to średnik ; i jest sens sprawdzac
                    if (temp.length() > 0) {
                        wystapilaZmiana = true;
                        if (wystapilWHERE == false) {
                            wystapilaNazwaTablicy = true;
                            nazwaTablicy = temp;
                            System.out.println("Wystąpił ';' nie ma 'WHERE'");
                            System.out.println("Nazwa tablicy: \n" + nazwaTablicy);
                            break;
                        } else if (wystapilWHERE == true) {
                            warunek = temp;
                            System.out.println("Wystąpił ';' 'WHERE' i jest warunek ");
                            System.out.println("Nazwa tablicy: " + nazwaTablicy + "\n Warunek: " + warunek);
                            break;
                        }
                    } else if (wystapilaNazwaTablicy == true) {
                        throw new Exception("Oczekiwano warunku");
                    } else
                        throw new Exception("Oczekiwano nazwy tabeli");
                }
            }

            // _WHERE, to całe contains jeszcze musi mieć spacje po wyrazeniu, tragedia
            if (temp.contains(" WHERE") == true && wystapilWHERE == false) {
                wystapilWHERE = true;
                wystapilaZmiana = true;
                wystapilaNazwaTablicy = true;
                nazwaTablicy = temp.substring(0, temp.length() - 6);
                System.out.println("Wystąpił WHERE, nazwa tablicy: " + nazwaTablicy);
                temp = "";
                continue;
            }

            // dowolne wystąpienie WHERE poraz drugi
            if (wystapilWHERE == true && temp.contains(" WHERE") == true) {
                throw new Exception("Błąd składni, dwukrotnie wystąpiło słowo kluczowe WHERE!");
            }

            temp += zbiorSymboli[i];
        }

        if (wystapilaZmiana == false) {
            throw new Exception("Błąd składni!");
        }

        nazwaTablicy = nazwaTablicy.replaceAll(" ", "");
        warunek = warunek.replaceAll(" ", "");

        // Map<String, String> nazwaKolumnyTrescWarunku = new TreeMap<>();
        // String[] oddzielneWarunki = warunek.split(",");

        // if (wystapilWHERE == true) {
        // // warunkow moze byc duzo i tylko przecinek je rozdziela
        // for (String pojedynczyWarunek : oddzielneWarunki) {
        // if (pojedynczyWarunek.contains("=") == true) {
        // String[] splited = pojedynczyWarunek.split("=");
        // String klucz = splited[0].replaceAll(" ", "");
        // String wartosc = splited[1].replaceAll(" ", "");
        // System.out.println("klucz: " + klucz + " wartosc: " + wartosc);
        // // kolumnaWarunek.put(klucz, wartosc);
        // } else
        // throw new Exception("Oczekiwano nazwa_kolumny = tresc;");
        // }

        if (wystapilWHERE == true) {
            boolean istnieje = czyIstniejeTabela(nazwaTablicy);
            if (istnieje == false)
                throw new Exception("Nazwa tablicy nie istnieje");
            if (warunek.contains("=") == false)
                throw new Exception("Warunek jest niepoprawny, spodziewano sie: kolumna = tresc");
            // spoko, mamy OSOBNO nazwe kolumny i tresc, ktora ma spelniac
            String[] kolumnaTresc = warunek.split("=");
            Tabela tabela = new Tabela();
            tabela = zbiorTabel.get(nazwaTablicy);
            try {
                tabela.usunWiersz(kolumnaTresc[0], kolumnaTresc[1]);
                // zzaaaaaaaktualizuj
                zbiorTabel.put(nazwaTablicy, tabela);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            // dziala
            // nie warunku, usun wszystkie wartosci z tabeli o danej nazwie;
            // It is possible to delete all rows in a table without deleting the table. This
            // means that the table structure, attributes, and indexes will be intact:
            // DELETE FROM table_name;
            boolean istnieje = czyIstniejeTabela(nazwaTablicy);
            if (istnieje == false)
                throw new Exception("Nazwa tablicy nie istnieje");
            Tabela tabela = new Tabela();
            tabela = zbiorTabel.get(nazwaTablicy);
            // usun wszysztko ale zostaw kolumny
            tabela.usunWartosciZKolumn();
            // zzaaaaaaaktualizuj
            zbiorTabel.put(nazwaTablicy, tabela);
        }
    }

    // działa, spoko
    public void insert(String komenda) throws Exception {
        // INSERT INTO table_name
        // VALUES (value1, value2, value3, ...);
        // SYNTAX wlasciwy + spacja "INSERT_INTO_"
        komenda = komenda.substring(12);
        char[] zbiorSymboli = komenda.toCharArray();

        boolean wystapilNawiasOtwierajacy = false;
        boolean wystapilNawiasZamykajacy = false;
        boolean wystapilaNazwaTablicy = false;
        boolean wystapilVALUES = false;
        boolean wystapilaZmiana = false;

        String temp = "";
        String nazwaTablicy = "";
        String wartosci = "";

        // nosz, to wyszło mi najlepiej, chyba najbardziej mi się chciało...
        // CREATE SYNTAX CHECKER
        for (int i = 0; i < zbiorSymboli.length; i++) {

            // ; - wyjscie
            if (i == zbiorSymboli.length - 1 || zbiorSymboli[i] == ';' || wystapilNawiasZamykajacy == true) {
                if (zbiorSymboli[i] != ';')
                    throw new Exception("Oczekiwano ');'");
                else {
                    // to średnik ; i jest sens sprawdzac
                    if (wystapilNawiasOtwierajacy == true && wystapilNawiasZamykajacy == true && wystapilVALUES == true
                            && wystapilaNazwaTablicy == true) {
                        wystapilaZmiana = true;
                        break;
                    } else
                        throw new Exception("Błąd składni, oczekiwano nazwa VALUES (wartosci);");
                }
            }

            if (temp.contains(" VALUES")) {
                if (wystapilVALUES == true)
                    throw new Exception("Błąd skłądni, dwukrotnie wystąpienie VALUES");
                nazwaTablicy = temp.substring(0, temp.length() - 7); // usuwam _VALUES
                if (nazwaTablicy.matches(".*[a-z].*")) {
                    // this will check if the string contains at least one character a-z
                    // Do something
                    wystapilaNazwaTablicy = true;
                    temp = "";
                    wystapilVALUES = true;
                    continue;
                } else
                    throw new Exception("Nazwa tablicy musi zawierac znaki a-z!");
            }

            if (zbiorSymboli[i] == '(') {
                if (wystapilNawiasOtwierajacy == true)
                    throw new Exception("Błąd składni, dwukrotne wystąpienie '('");
                wystapilNawiasOtwierajacy = true;
                continue;
            }

            if (zbiorSymboli[i] == ')') {
                if (wystapilNawiasZamykajacy == true)
                    throw new Exception("Błąd składni, dwukrotne wystąpienie ')'");
                wystapilNawiasZamykajacy = true;
                wartosci = temp;
                temp = "";
                continue;
            }
            temp += zbiorSymboli[i];
        }
        // INSERT INTO ... ( value1, value2 ... );
        // SYNTAX CHECKER
        if (wystapilaZmiana == false) {
            throw new Exception("Błąd składni!");
        }
        // rozgrywka wygrana!
        temp = "";

        // po wszystkim i tak kolejne sortowanie wynikow
        // te SPACJE mnie zabiją
        nazwaTablicy = nazwaTablicy.replaceAll(" ", "");
        wartosci = wartosci.replaceAll(" ", "");
        String[] zbiorWartosci = wartosci.split(",");
        System.out.println("Nazwa tablicy: " + nazwaTablicy);
        System.out.println("WARTOSCI: ");
        System.out.println(wartosci);

        Tabela tabela = new Tabela();
        if (czyIstniejeTabela(nazwaTablicy) == false)
            throw new Exception("Nie istnieje taka tablica");
        tabela = zbiorTabel.get(nazwaTablicy);
        tabela.dodajWartosciDoKolumn(zbiorWartosci);

        zbiorTabel.put(nazwaTablicy, tabela);
    }

    // działa częściowo, przy WHERE robi inf loop 
    // TODO: do poprawy WHERE
    public void select(String komenda) throws Exception {
        // SELECT column1, column2, ...
        // FROM table_name;

        // SELECT column1, column2, ...
        // FROM table_name
        // WHERE condition;
        // SYNTAX wlasciwy + spacja "SELECT_"
        komenda = komenda.substring(7);
        char[] zbiorSymboli = komenda.toCharArray();

        boolean wystapilaNazwaTablicy = false;
        boolean wystapilFROM = false;
        boolean wystapilWHERE = false;
        boolean wystapilaZmiana = false;
        boolean wystapilyKolumny = false;

        String temp = "";
        String kolumny = "";
        String nazwaTablicy = "";
        String warunek = "";

        // SELECT SYNTAX CHECKER
        for (int i = 0; i < zbiorSymboli.length; i++) {
            // ; - wyjscie
            if (i == zbiorSymboli.length - 1 || zbiorSymboli[i] == ';') {
                if (zbiorSymboli[i] != ';')
                    throw new Exception("Oczekiwano ';'");
                else {
                    // to średnik ; i jest sens sprawdzac
                    if (wystapilFROM == true & wystapilWHERE == true) {
                        if (temp.matches(".*[a-z].*")) {
                            warunek = temp; // bez srednika
                            wystapilaZmiana = true;
                            break;
                        } else
                            throw new Exception("Wprowadz warunek");
                    }
                    if (wystapilyKolumny == true) {
                        if (temp.matches(".*[a-z].*")) {
                            nazwaTablicy = temp; // bez srednika
                            wystapilaZmiana = true;
                            break;
                        } else
                            throw new Exception("Wprowadz nazwe tablicy");
                    }
                }
            }

            if (temp.contains(" FROM")) {
                if (wystapilFROM == true)
                    throw new Exception("Błąd składni, dwukrotne wystąpienie FROM!");
                wystapilFROM = true;
                kolumny = temp.substring(0, temp.length() - 5);
                if (kolumny.matches(".*[a-z].*") || kolumny.contains("*")) {
                    wystapilyKolumny = true;
                    temp = "";
                    continue;
                } else
                    throw new Exception("Kolumny nie zawieraja liter");
            }

            if (temp.contains(" WHERE")) {
                if (wystapilWHERE == true)
                    throw new Exception("Błąd składni, dwukrotne wystąpienie WHERE!");

                wystapilWHERE = true;
                nazwaTablicy = temp.substring(0, temp.length() - 6);
                if (nazwaTablicy.matches(".*[a-z].*")) {
                    wystapilaNazwaTablicy = true;
                    temp = "";
                    continue;
                } else
                    throw new Exception("Nazwa tablicy nie zawiera liter");
            }

            temp += zbiorSymboli[i];
        }

        if (wystapilaZmiana == false)
            throw new Exception("Błąd składni!");

        if (czyIstniejeTabela(nazwaTablicy) == false)
            throw new Exception("Nie ma takiej tablicy");

        temp = "";

        String[] zbiorKolumn = kolumny.replaceAll(" ", "").split(",");
        String[] warunekKolumnaWartosc = warunek.replaceAll(" ", "").split("=");

        if (wystapilWHERE == true) {
            if (warunek.contains("=") == false)
                throw new Exception("Warunek musi skladac się z kolumna = tresc");
            Tabela tabela = new Tabela();
            tabela = zbiorTabel.get(nazwaTablicy);
            tabela.wypiszKolumnyZTablicyGdzieKolumnaSpelniaWarunek(zbiorKolumn, warunekKolumnaWartosc);

        } else {
            // wypisz wszystkie kolumny / wypisz kolumne
            Tabela tabela = new Tabela();
            tabela = zbiorTabel.get(nazwaTablicy);

            for (String kolumna : zbiorKolumn) {
                if (kolumna.equals("*")) {
                    tabela.wypiszWszystkieKolumnyWrazZZawaroscia();
                    break;
                } else
                    tabela.wypiszZawartoscKolumny(kolumna);
            }
        }
    }

    public Map<String, Tabela> update(String nazwaTabeli) {
        // UPDATE table_name
        // SET column1 = value1, column2 = value2, ...
        // WHERE condition;
        boolean istniejeTabela = czyIstniejeTabela(nazwaTabeli);

        return this.zbiorTabel;
    }

    public Map<String, Tabela> zmienNazweTabeli(String staraNazwa, String nowaNazwa) {
        boolean istniejeTabela = czyIstniejeTabela(staraNazwa);
        if (!istniejeTabela) {
            System.out.println("Tabela " + staraNazwa + " nie istnieje");
            return this.zbiorTabel;
        }

        // istnieje
        Tabela tabela = new Tabela();
        tabela = this.zbiorTabel.get(staraNazwa);

        this.zbiorTabel.remove(staraNazwa);
        this.zbiorTabel.put(nowaNazwa, tabela);

        return this.zbiorTabel;
    }

    private boolean czyIstniejeTabela(String nazwaTabeli) {
        Set<String> zbiorTabelKeys = zbiorTabel.keySet();
        for (String zbiorTabelKey : zbiorTabelKeys) {
            if (zbiorTabelKey.equals(nazwaTabeli) == true)
                return true;
        }

        return false;
    }

    private void wypiszWszystkieDostepneNazwyTabel() {
        Set<String> nazwyTabel = zbiorTabel.keySet();
        System.out.println("Wszystkie nazwy tabel znajduja sie pozniej");
        System.out.println(nazwyTabel);
    }

    private void wypiszTabliceStringow(String[] strings) {
        for (String string : strings) {
            System.out.println(string);
        }
    }
}

// // SELECT column1, column2, ...
// // FROM table_name;

// // SELECT * FROM table_name;

// if(command.startsWith("SELECT"))
// {
// System.out.println(command);
// String[] details = command.split(" ");
// for (String string : details) {
// System.out.println(string);
// }

// int indexOfFROM = details.length - 2;
// if (details[indexOfFROM].equals("FROM")) {
// System.out.println("Jest FROM");
// } else
// throw new Exception("Syntax error");

// String nazwaTabeli = details[details.length - 1];
// }