package app;

public class App {
    public static void main(String[] args) throws Exception {

        ZbiorTabel tabele = new ZbiorTabel();

        tabele.create("nazwaTabeli");
        tabele.select("nazwaTabeli");

        tabele.insert("nazwaTabeli");

        tabele.select("nazwaTabeli");
        // tabele.create("nazwaTabeli");

        System.out.println("Hello Java");
    }
}