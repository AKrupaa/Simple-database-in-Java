package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Tabela {
    // ~~ nazwaKolumny, zawartoscTejKolumny ~~ tabela
    Map<String, List<String>> tabela;

    public Tabela() {
        tabela = new TreeMap<>();
    }

    // SPRAWNE - nie dotykać
    public void dodajKolumne(String nazwaKolumny) {
        // sprawdz czy taka juz nie istnieje
        boolean czyIstnieje = sprawdzCzyJuzIstniejeKolumna(nazwaKolumny);
        if (czyIstnieje) {
            System.out.println("Kolumna o podanej nazwie: " + nazwaKolumny + " już istnieje!");
        }

        // nie istnieje

        // dodaj nową kolumnę i pustą zawartość
        List<String> zawartoscKolumny = new ArrayList<>();
        this.tabela.put(nazwaKolumny, zawartoscKolumny);
    }

    // SPRAWNE - nie dotykać
    public void dodajWartoscDoKolumny(String nazwaKolumny, String wartosc) {
        boolean znalezionoKolumne = znajdzKolumne(nazwaKolumny);
        boolean zawartoscKolumnyJestPusta = czyZawartoscKolumnyJestPusta(nazwaKolumny);

        List<String> zawartoscKolumny = new ArrayList<>();

        if (znalezionoKolumne) {

            if (zawartoscKolumnyJestPusta) {
                zawartoscKolumny.add(wartosc);
                this.tabela.put(nazwaKolumny, zawartoscKolumny);
            } else {
                zawartoscKolumny = tabela.get(nazwaKolumny);
                zawartoscKolumny.add(wartosc);
                this.tabela.put(nazwaKolumny, zawartoscKolumny);
            }
        } else {
            System.out.println("Nie znaleziono kolumny: " + nazwaKolumny);
        }
    }

    // SPRAWNE - nie dotykać
    public Map<String, List<String>> usunKolumne(String nazwaKolumny) {
        if (znajdzKolumne(nazwaKolumny)) {
            // znaleziono
            tabela.remove(nazwaKolumny);
            return this.tabela;
        }

        // nie znaleziono -> wyjatek
        System.out.println("Nie znaleziono kolumny" + nazwaKolumny);
        return this.tabela;
    }

    // SPRAWNE - nie dotykać
    public Map<String, List<String>> usunWartoscZKolumny(String nazwaKolumny, int index) {
        boolean znalezionoKolumneOrazCzyNieJestPusta;
        try {
            znalezionoKolumneOrazCzyNieJestPusta = czyZnalezionoKolumneOrazCzyNieJestPusta(nazwaKolumny);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            znalezionoKolumneOrazCzyNieJestPusta = false;
        }

        if (znalezionoKolumneOrazCzyNieJestPusta) {
            List<String> zawartoscKolumny = tabela.get(nazwaKolumny);

            try {
                zawartoscKolumny.remove(index);
                tabela.put(nazwaKolumny, zawartoscKolumny);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }

        }

        return this.tabela;
    }

    // SPRAWNE - nie dotykać
    public void wypiszWszystkieKolumny() {
        System.out.println("Wszystkie dostępne kolumny");
        Set<String> tabelaKeys = this.tabela.keySet();
        System.out.println(tabelaKeys);
    }

    // SPRAWNE - nie dotykać
    public void wypiszZawartoscKolumny(String nazwaKolumny) {

        try {
            czyZnalezionoKolumneOrazCzyNieJestPusta(nazwaKolumny);
            // znaleziono i nie jest pusta
            List<String> zawartoscKolumny;
            zawartoscKolumny = tabela.get(nazwaKolumny);
            // zawartoscKolumny;
            if (zawartoscKolumny.size() != 0) {
                System.out.println("Zawartosc kolumny " + nazwaKolumny + " to:");
                for (int i = 0; i < zawartoscKolumny.size(); i++)
                    System.out.println("Indeks " + i + ": " + zawartoscKolumny.get(i));
            } else
                System.out.println("Zawartosc kolumny " + nazwaKolumny + " jest pusta!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    // SPRAWNE - nie dotykać
    private boolean znajdzKolumne(String nazwaKolumny) {
        Set<String> tabelaKeys = this.tabela.keySet();

        for (String tabelaKey : tabelaKeys) {
            if (tabelaKey.compareTo(nazwaKolumny) == 0) {
                return true;
            }
        }
        return false;
    }

    // SPRAWNE - nie dotykać
    private boolean sprawdzCzyJuzIstniejeKolumna(String nazwaKolumny) {
        Set<String> tabelaKeys = this.tabela.keySet();
        int counter = 0;
        for (String tabelaKey : tabelaKeys) {
            if (tabelaKey.compareTo(nazwaKolumny) == 0) {
                counter = counter + 1;
            }
        }

        if (counter > 0)
            return true; // wystapil duplikat
        else
            return false; // nie ma duplikatu
    }

    // SPRAWNE - nie dotykać
    private boolean czyZawartoscKolumnyJestPusta(String nazwaKolumny) {
        if (tabela.get(nazwaKolumny) == null)
            return true;
        else
            return false;
    }

    // SPRAWNE - nie dotykać
    private boolean czyZnalezionoKolumneOrazCzyNieJestPusta(String nazwaKolumny) throws Exception {
        // znaleziono ale jest pusta
        if (znajdzKolumne(nazwaKolumny) && czyZawartoscKolumnyJestPusta(nazwaKolumny))
            throw new Exception("Zawartosc kolumny " + nazwaKolumny + " jest akutalnie pusta");

        // nie znaleziono
        if (!znajdzKolumne(nazwaKolumny))
            throw new Exception("Nie znaleziono kolumny " + nazwaKolumny);

        // znaleziono
        return true;
    }
}