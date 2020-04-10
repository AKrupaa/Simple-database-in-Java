package app;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

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

        } else if (command.startsWith("DELETE")) {
            delete(command);
        } else if (command.startsWith("SELECT ")) {
            select(command);
        } else if (command.startsWith("UPDATE ")) {
            update(command);
        }
    }

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
        if(tempZmieniony == false) {
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
        //     temp = zbiorNazwKolumn.get(i);
        //     temp = temp.replaceAll(" ", "");
        //     zbiorNazwKolumn.set(i, temp);
        //     System.out.println(temp);
        // }

        // dodaj "sensowne" dane
        Tabela tabela = new Tabela();
        for (String string : zbiorNazwKolumn) {
            tabela.dodajKolumne(string);
        }
        // umiesc i elo
        zbiorTabel.put(nazwaTablicy, tabela);
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

    public void delete(String komenda) {
        // DELETE FROM table_name WHERE condition;

        // SYNTAX wlasciwy + spacja "DELETE_FROM_"
        komenda = komenda.substring(12);
        char[] zbiorSymboli = komenda.toCharArray();

        boolean wystapilaNazwaTablicy = false;
        boolean wystapil = false;
        // boolean wystapilNawiasZamykajacy = false;

        ArrayList<String> zbiorNazwKolumn = new ArrayList<String>();
        String nazwaTablicy = "";
        String temp = "";

    }

    public Map<String, Tabela> update(String nazwaTabeli) {
        // UPDATE table_name
        // SET column1 = value1, column2 = value2, ...
        // WHERE condition;
        boolean istniejeTabela = czyIstniejeTabela(nazwaTabeli);

        return this.zbiorTabel;
    }

    public Map<String, Tabela> insert(String nazwaTabeli) throws Exception {
        boolean istniejeTabela = czyIstniejeTabela(nazwaTabeli);
        return this.zbiorTabel;
    }

    public void select(String nazwaTabeli) {
        // SELECT column1, column2, ...
        // FROM table_name;
        // SELECT * FROM table_name;
        boolean istniejeTabela = czyIstniejeTabela(nazwaTabeli);
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
            if (zbiorTabelKey == nazwaTabeli)
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