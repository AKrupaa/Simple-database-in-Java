package app;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class ZbiorTabel {
    private Map<String, Tabela> zbiorTabel;
    private Scanner scanner = new Scanner(System.in);

    public ZbiorTabel() {
        this.zbiorTabel = new TreeMap<>();
    }


    public Map<String, Tabela> insert(String nazwaTabeli) throws Exception {
        boolean istniejeTabela = czyIstniejeTabela(nazwaTabeli);
        if (!istniejeTabela) {
            wypiszWszystkieDostepneNazwyTabel();
            throw new Exception("Tabela o nazwie: " + nazwaTabeli + " nie istnieje!");
        }

        if(zbiorTabel.get(nazwaTabeli) == null) {
            wypiszWszystkieDostepneNazwyTabel();
            throw new Exception("Tabela o nazwie: " + nazwaTabeli + " nie posiada kolumn!");
        }

        Tabela tabela = new Tabela();
        tabela = zbiorTabel.get(nazwaTabeli);
        tabela.wypiszWszystkieKolumnyWrazZZawaroscia();

        System.out.println("Wprowadz nazwe tabeli aby dodac wartosci");
        String decyzja = scanner.nextLine();
        boolean czyIstniejeKolumna = tabela.znajdzKolumne(decyzja);

        if(!czyIstniejeKolumna){
            throw new Exception("Nie istnieje taka tabela");
        }

        while(true) {
            System.out.println("Wprowadz wartosc ");
            String wartosc = scanner.nextLine();
            tabela.dodajWartoscDoKolumny(decyzja, wartosc);

            System.out.println("Wprowadzic kolejna wartosc? 'tak' 'nie'");

            String wprowadzic = scanner.nextLine();
            if (wprowadzic.compareTo("tak") == 0){
                continue;
            }
            if (wprowadzic.compareTo("nie") == 0)
                break;
        }

        this.zbiorTabel.put(nazwaTabeli, tabela);

        return this.zbiorTabel;
    }

    public void select(String nazwaTabeli) throws Exception {
        boolean istniejeTabela = czyIstniejeTabela(nazwaTabeli);
        if (!istniejeTabela) {
            wypiszWszystkieDostepneNazwyTabel();
            throw new Exception("Tabela o nazwie: " + nazwaTabeli + " nie istnieje!");
        }

        if(zbiorTabel.get(nazwaTabeli) == null) {
            wypiszWszystkieDostepneNazwyTabel();
            throw new Exception("Tabela o nazwie: " + nazwaTabeli + " nie posiada kolumn!");
        }
        // istnieje
        Tabela tabela = new Tabela();
        tabela = zbiorTabel.get(nazwaTabeli);
        
        System.out.println("Wypisać wszystkie kolumny? 'k' Wypisać cala zawartosc? 'z'");
        String decyzja = scanner.nextLine();
        if (decyzja.compareTo("k") == 0){
            tabela.wypiszWszystkieKolumny();
        }
        if (decyzja.compareTo("z") == 0)
            tabela.wypiszWszystkieKolumnyWrazZZawaroscia();

        if(decyzja.compareTo("k") != 0 || decyzja.compareTo("z") != 0) {
            System.out.println("Nie podjeto zadnych dzialan");
        }
    }

    public Map<String, Tabela> delete(String nazwaTabeli) {
        // The DELETE statement is used to delete existing records in a table.
        boolean istniejeTabela = czyIstniejeTabela(nazwaTabeli);
        if (!istniejeTabela)
            return this.zbiorTabel;
        // istnieje
        System.out.println("Czy usunac cala tabele? Wpisz 'tak' lub 'nie' jezeli chcesz zrezygnowac.");
        String decyzja = scanner.nextLine();
        if (decyzja.compareTo("tak") == 0){
            this.zbiorTabel.remove(nazwaTabeli);
            System.out.println("Usunieto tabele: " + nazwaTabeli);
        }
        if (decyzja.compareTo("nie") == 0)
            System.out.println("Zrezygnowales");

        return this.zbiorTabel;
    }

    public void create(String nazwaTabeli) {
        boolean istniejeTabela = czyIstniejeTabela(nazwaTabeli);
        if (istniejeTabela) {
            System.out.println("Tabela o nazwie: " + nazwaTabeli + " juz istnieje!");
        } else {
            // nie istnieje tabela
            // dodajmy kolumny
            Tabela tabela = new Tabela();
            while (true) {
                String nazwaKolumny;
                System.out.print("Podaj nazwe kolumny: ");
                nazwaKolumny = scanner.nextLine();
                tabela.dodajKolumne(nazwaKolumny);
                System.out.println("Czy dodac kolejna kolumne? Wpisz 'tak'");
                System.out.println("Jezeli chcesz wyjsc wpisz - 'wyjdz'");
                String decyzja = scanner.nextLine();
                if (decyzja.compareTo("tak") == 0)
                    continue;
                if (decyzja.compareTo("wyjdz") == 0)
                    break;
            }
            zbiorTabel.put(nazwaTabeli, tabela);
        }
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
}