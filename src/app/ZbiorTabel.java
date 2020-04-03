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

    public void select(String nazwaKolumny, String nazwaTabeli) {
        boolean istniejeTabela = czyIstniejeTabela(nazwaTabeli);
        if (!istniejeTabela) {
            System.out.println("Tabela o nazwie: " + nazwaTabeli + " nie istnieje!");
        }

        // istnieje
        Tabela tabela = new Tabela();

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

    public Map<String, Tabela> delete(String nazwaTabeli) {
        boolean istniejeTabela = czyIstniejeTabela(nazwaTabeli);
        if (!istniejeTabela)
            return this.zbiorTabel;

        // istnieje
        this.zbiorTabel.remove(nazwaTabeli);
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

    private boolean czyIstniejeTabela(String nazwaTabeli) {
        Set<String> zbiorTabelKeys = zbiorTabel.keySet();
        for (String zbiorTabelKey : zbiorTabelKeys) {
            if (zbiorTabelKey == nazwaTabeli)
                return true;
        }

        return false;
    }
}