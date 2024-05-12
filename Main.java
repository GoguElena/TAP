import java.util.Scanner;
import java.util.Stack;

class Record {
    private String data;

    public Record(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Record: " + data;
    }
}

class StackEmptyException extends Exception {
    public StackEmptyException(String message) {
        super(message);
    }
}

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<Record> stack = new Stack<>();
        boolean running = true;

        while (running) {
            System.out.println("1. Adaugă element în stivă");
            System.out.println("2. Elimină element din stivă");
            System.out.println("3. Afișează elementul din vârful stivei");
            System.out.println("4. Afișează întreaga stivă");
            System.out.println("5. Ieșire din program");
            System.out.print("Alege o opțiune: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consumă newline
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine(); // Consumă newline
                System.out.println(ANSI_RED + "Excepție: Introduceți un număr valid (1-5)!" + ANSI_RESET);
                continue; // Continuă bucla pentru a cere o altă opțiune
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Introdu un element: ");
                        String data = scanner.nextLine();
                        Record record = new Record(data);
                        stack.push(record);
                        System.out.println("Element adăugat în stivă.");
                        break;
                    case 2:
                        if (stack.isEmpty()) {
                            throw new StackEmptyException("Stiva este goală!");
                        }
                        System.out.println("Stiva conține următoarele elemente:");
                        for (int i = 0; i < stack.size(); i++) {
                            System.out.println((i + 1) + ". " + stack.get(i));
                        }
                        System.out.print("Alege indexul elementului de eliminat: ");
                        int indexToRemove;
                        try {
                            indexToRemove = scanner.nextInt();
                            scanner.nextLine(); // Consumă newline
                        } catch (java.util.InputMismatchException ex) {
                            scanner.nextLine(); // Consumă newline
                            throw new IllegalArgumentException("Excepție: Introduceți un număr valid!");
                        }
                        if (indexToRemove < 1 || indexToRemove > stack.size()) {
                            throw new IllegalArgumentException("Excepție: Indexul este invalid!");
                        }
                        Record removedRecord = stack.remove(indexToRemove - 1);
                        System.out.println("Element eliminat din stivă: " + removedRecord.getData());
                        break;
                    case 3:
                        if (stack.isEmpty()) {
                            throw new StackEmptyException("Stiva este goală!");
                        }
                        Record topRecord = stack.peek();
                        System.out.println("Elementul din vârful stivei: " + topRecord.getData());
                        break;
                    case 4:
                        if (stack.isEmpty()) {
                            throw new StackEmptyException("Stiva este goală!");
                        }
                        System.out.println("Stiva conține următoarele elemente:");
                        for (Record item : stack) {
                            System.out.println(item.getData());
                        }
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println(ANSI_RED + "Opțiune invalidă! Alege din nou." + ANSI_RESET);
                }
            } catch (StackEmptyException e) {
                System.out.println(ANSI_RED + "Excepție: " + e.getMessage() + ANSI_RESET);
            } catch (IllegalArgumentException e) {
                System.out.println(ANSI_RED + "Excepție: " + e.getMessage() + ANSI_RESET);
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Excepție: " + e.getMessage() + ANSI_RESET);
            }
        }

        scanner.close();
        System.out.println("Program încheiat.");
    }
}
