package app;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        ZbiorTabel tabele = new ZbiorTabel();
        Scanner scanner = new Scanner(System.in); // Create a Scanner object

        while (true) {
            String command = scanner.nextLine();
            if (command.equals("exit"))
                break;
                
            tabele.syntaxHandler(command);
        }

        scanner.close();
    }
}