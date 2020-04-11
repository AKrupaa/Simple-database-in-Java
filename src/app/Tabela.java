package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Tabela {
    // ~~ nazwaKolumny, zawartoscTejKolumny ~~ tabela
    private Map<String, List<String>> tabela;

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
    public void dodajWartoscDoKolumny(String nazwaKolumny, String wartosc) throws Exception {
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
            throw new Exception("Nie znaleziono kolumny: " + nazwaKolumny);
        }
    }

    public void dodajWartosciDoKolumn(String[] zbiorWartosci) {
        // Set<Entry<String, List<String>>> entry = tabela.entrySet();
        int i = 0;
        for (Entry<String, List<String>> entry : tabela.entrySet()) {
            // dla kazdej kolumny, wez i wstaw, jezeli nie masz co wstawic, wstaw ""
            List<String> lista = entry.getValue();
            if (i == zbiorWartosci.length)
                lista.add("");

            while (i < zbiorWartosci.length) {
                lista.add(zbiorWartosci[i]);
                i++;
                break;
            }
            tabela.put(entry.getKey(), lista);
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

    public void usunWartosciZKolumn() {

        // Set<Entry<String, List<String>>> entry = tabela.entrySet();

        for (Entry<String, List<String>> entry : tabela.entrySet()) {
            List<String> nowaZawartoscKolumny = entry.getValue();
            nowaZawartoscKolumny.clear();
            tabela.put(entry.getKey(), nowaZawartoscKolumny);
        }
    }

    public void usunWiersz(String kolumna, String wartosc) throws Exception {
        boolean istnieje = sprawdzCzyJuzIstniejeKolumna(kolumna);
        if (istnieje == false)
            throw new Exception("Nie istnieje taka kolumna " + kolumna);

        boolean zawiera = false;
        int indexOfValue = 0;
        List<String> zawartoscKolumny = tabela.get(kolumna);
        for (String string : zawartoscKolumny) {
            if (string.equals(wartosc)) {
                zawiera = true;
                break;
            }
            indexOfValue++;
        }

        if (zawiera == true) {
            for (Entry<String, List<String>> entry : tabela.entrySet()) {
                List<String> nowaZawartoscKolumny = entry.getValue();
                nowaZawartoscKolumny.remove(indexOfValue);
                tabela.put(entry.getKey(), nowaZawartoscKolumny);
            }
        }
    }

    // SPRAWNE - nie dotykać
    public void wypiszWszystkieKolumny() {
        System.out.println("Wszystkie dostępne kolumny");
        Set<String> tabelaKeys = this.tabela.keySet();
        System.out.println(tabelaKeys);
    }

    public void wypiszWszystkieKolumnyWrazZZawaroscia() {
        Set<Entry<String, List<String>>> entires = tabela.entrySet();
        for (Entry<String, List<String>> ent : entires) {
            System.out.println(ent.getKey() + " ==> " + ent.getValue());
        }
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

    public void wypiszKolumnyZTablicyGdzieKolumnaSpelniaWarunek(String[] zbiorKolumn, String[] warunekKolumnaWartosc) {
        
        // wcale nie robię niezrozumiałych zagnieżdżeń
        
        boolean wypiszWszystkieKolumny = false;
        for (String kolumna : zbiorKolumn) {
            if (kolumna.equals("*")) {
                wypiszWszystkieKolumny = true;
                break;
            }
        }

        if (wypiszWszystkieKolumny == true) {
            // wypisz wszystkie kolumny, ale tylko rzad gdzie wystapil ten ... warunek
            String warunekKolumna = warunekKolumnaWartosc[0];
            String warunekWartosc = warunekKolumnaWartosc[1];

            // poszczegolne kolumny do wypisania
            // for (String kolumna : zbiorKolumn) {
            // kolumny
            if (tabela.containsKey(warunekKolumna)) {
                // posiada taka kolumne gdzie nalezy sprawdzic warunek
                // pobierz zawartosc kolumny
                List<String> zawartoscKolumny = tabela.get(warunekKolumna);
                int index = 0;
                // dopoki nie wyszedles ze ZBIORU WARTOSCI DANEJ KOLUMNY
                while (index < zawartoscKolumny.size())
                    // jezeli kolumna Y posiada wartosc X ( Imie ?= Arkadiusz )
                    // na miejscu index
                    if (zawartoscKolumny.get(index).equals(warunekWartosc)) {
                        // wypisz teraz wszystkie rzedy, wlacznie z nazwami ... kolumn
                        // Set<Entry<String, List<String>>> entry = tabela.entrySet();
                        for (Entry<String, List<String>> ent : tabela.entrySet()) {
                            // System.out.println(ent.getKey() + " ==> " + ent.getValue());
                            // wypisz wszystkie kolumny, ale tylko rzad gdzie wystapil ten ... warunek
                            System.out.println("Kolumna: " + ent.getKey() + " ==> " + ent.getValue().get(index));
                        }
                    }
                index++;
            }
            // }
        } else {
            // wypisz TYLKO poszczegolne KOLUMNY oraz RZEDY
            // lalalalalalaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

            String warunekKolumna = warunekKolumnaWartosc[0];
            String warunekWartosc = warunekKolumnaWartosc[1];
            // poszczegolne kolumny do wypisania
            for (String kolumna : zbiorKolumn) {
                if (tabela.containsKey(warunekKolumna)) {
                    // posiada taka kolumne gdzie nalezy sprawdzic warunek
                    // pobierz zawartosc kolumny
                    List<String> zawartoscKolumny = tabela.get(warunekKolumna);
                    int index = 0;
                    // dopoki nie wyszedles ze ZBIORU WARTOSCI DANEJ KOLUMNY
                    while (index < zawartoscKolumny.size())
                        // jezeli kolumna Y posiada wartosc X ( Imie ?= Arkadiusz )
                        // na miejscu index
                        if (zawartoscKolumny.get(index).equals(warunekWartosc)) {
                            // wypisz teraz wszystkie rzedy, wlacznie z nazwami ... kolumn
                            // Set<Entry<String, List<String>>> entry = tabela.entrySet();
                            for (Entry<String, List<String>> ent : tabela.entrySet()) {
                                // System.out.println(ent.getKey() + " ==> " + ent.getValue());
                                // wypisz WYBRANE kolumny, ale tylko rzad gdzie wystapil ten ... warunek
                                // lalala.
                                if (ent.getKey().equals(kolumna))
                                    System.out.println("Kolumna: " + ent.getKey() + " ==> " + ent.getValue().get(index));
                            }
                        }
                    index++;
                }
            }
        }
    }

    // SPRAWNE - nie dotykać
    public boolean znajdzKolumne(String nazwaKolumny) {
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
    public boolean czyZnalezionoKolumneOrazCzyNieJestPusta(String nazwaKolumny) throws Exception {
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