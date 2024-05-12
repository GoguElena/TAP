import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface FractionInterface {
    void enterFraction(Scanner scanner);
    void displayFraction();
    FractionInterface add(FractionInterface other);
    FractionInterface subtract(FractionInterface other);
}

interface FractionArrayInterface {
    void addFraction(FractionInterface fraction);
    void displayArray();
    FractionInterface sumFractions(List<FractionInterface> fractions);
    FractionInterface subtractFractions(List<FractionInterface> fractions);
    FractionInterface getFraction(int index);
    int size();
}

class Fraction implements FractionInterface {
    private int numerator;
    private int denominator;

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public void enterFraction(Scanner scanner) {
        System.out.print("Enter numerator: ");
        numerator = scanner.nextInt();
        System.out.print("Enter denominator: ");
        denominator = scanner.nextInt();
    }

    @Override
    public void displayFraction() {
        System.out.println(numerator + "/" + denominator);
    }

    @Override
    public FractionInterface add(FractionInterface other) {
        Fraction f = (Fraction) other;
        int resultNumerator = this.numerator * f.denominator + f.numerator * this.denominator;
        int resultDenominator = this.denominator * f.denominator;
        return new Fraction(resultNumerator, resultDenominator).simplify();
    }

    @Override
    public FractionInterface subtract(FractionInterface other) {
        Fraction f = (Fraction) other;
        int resultNumerator = this.numerator * f.denominator - f.numerator * this.denominator;
        int resultDenominator = this.denominator * f.denominator;
        return new Fraction(resultNumerator, resultDenominator).simplify();
    }

    private FractionInterface simplify() {
        int gcd = gcd(numerator, denominator);
        return new Fraction(numerator / gcd, denominator / gcd);
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}

class FractionArray implements FractionArrayInterface {
    private FractionInterface[] fractions;
    private int size;
    private int currentIndex;

    public FractionArray(int size) {
        this.size = size;
        fractions = new FractionInterface[size];
        currentIndex = 0;
    }

    @Override
    public void addFraction(FractionInterface fraction) {
        if (currentIndex < size) {
            fractions[currentIndex++] = fraction;
        } else {
            System.out.println("Array is full. Cannot add more fractions.");
        }
    }

    @Override
    public void displayArray() {
        System.out.println("Fraction Array:");
        for (int i = 0; i < currentIndex; i++) {
            System.out.print("[" + i + "] ");
            fractions[i].displayFraction();
        }
    }

    @Override
    public FractionInterface sumFractions(List<FractionInterface> fractions) {
        FractionInterface sum = new Fraction(0, 1);
        for (FractionInterface fraction : fractions) {
            if (fraction != null) {
                sum = sum.add(fraction);
            }
        }
        return sum;
    }

    @Override
    public FractionInterface subtractFractions(List<FractionInterface> fractions) {
        FractionInterface result = fractions.get(0);
        for (int i = 1; i < fractions.size(); i++) {
            if (fractions.get(i) != null) {
                result = result.subtract(fractions.get(i));
            }
        }
        return result;
    }

    @Override
    public FractionInterface getFraction(int index) {
        if (index >= 0 && index < currentIndex) {
            return fractions[index];
        }
        return null;
    }

    @Override
    public int size() {
        return currentIndex;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FractionArrayInterface fractionArray = new FractionArray(5);

        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addMultipleFractions(scanner, fractionArray);
                    break;
                case 2:
                    fractionArray.displayArray();
                    break;
                case 3:
                    performOperations(scanner, fractionArray);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            scanner.nextLine(); // Consume newline character
        } while (choice != 4);
    }

    private static void displayMenu() {
        System.out.println("============MENU===========");
        System.out.println("1. Enter Multiple Fractions");
        System.out.println("2. Display Fraction Array");
        System.out.println("3. Perform Operations");
        System.out.println("4. Exit");
        System.out.println("===========================");
    }

    private static void addMultipleFractions(Scanner scanner, FractionArrayInterface fractionArray) {
        System.out.print("Enter the number of fractions to add: ");
        int numFractions = scanner.nextInt();

        for (int i = 0; i < numFractions; i++) {
            FractionInterface newFraction = new Fraction(0, 1);
            newFraction.enterFraction(scanner);
            fractionArray.addFraction(newFraction);
        }
    }

    private static void performOperations(Scanner scanner, FractionArrayInterface fractionArray) {
        System.out.println("Choose fractions to perform operations (separated by spaces):");
        fractionArray.displayArray();
        System.out.print("Enter the indices of the fractions: ");
        scanner.nextLine(); // Consume newline character
        String indicesInput = scanner.nextLine();
        String[] indicesArray = indicesInput.split(" ");

        List<FractionInterface> selectedFractions = new ArrayList<>();
        for (String indexStr : indicesArray) {
            int index = Integer.parseInt(indexStr);
            if (index >= 0 && index < fractionArray.size()) {
                FractionInterface fraction = fractionArray.getFraction(index);
                selectedFractions.add(fraction);
            } else {
                System.out.println("Invalid fraction index: " + index);
                return; // Stop operations if any index is invalid
            }
        }

        if (selectedFractions.size() >= 2) {
            System.out.println("Performing operations between selected fractions...");
            System.out.println("1. Add Fractions");
            System.out.println("2. Subtract Fractions");
            System.out.print("Enter your choice: ");
            int operationChoice = scanner.nextInt();

            switch (operationChoice) {
                case 1:
                    FractionInterface sum = fractionArray.sumFractions(selectedFractions);
                    System.out.print("Result of addition: ");
                    sum.displayFraction();
                    break;
                case 2:
                    FractionInterface difference = fractionArray.subtractFractions(selectedFractions);
                    System.out.print("Result of subtraction: ");
                    difference.displayFraction();
                    break;
                default:
                    System.out.println("Invalid operation choice.");
            }
        } else {
            System.out.println("Insufficient fractions selected. Please select at least 2 fractions.");
        }
    }
}
