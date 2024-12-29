package org.example.Menedzer_finansow_osobistych;

import org.example.Menedzer_finansow_osobistych.Transaction;

import java.util.*;

// Główna klasa FinancialManager do zarządzania finansami
public class FinancialManager {
    private List<Transaction> transactions = new ArrayList<>(); // Lista przechowująca wszystkie transakcje
    private Map<String, Double> categoryExpenses = new HashMap<>(); // Mapa kategorii wydatków i ich sum
    private Set<String> uniqueDescriptions = new HashSet<>(); // Zbiór unikalnych opisów transakcji
    private double balance; // Obecne saldo użytkownika
    private String firstName; // Imię użytkownika
    private String lastName; // Nazwisko użytkownika

    // Historia zmian imienia i salda
    private List<String> nameHistory = new ArrayList<>(); // Lista zmian imienia
    private List<Double> balanceHistory = new ArrayList<>(); // Lista zmian salda

    // Konstruktor inicjalizujący manager finansów
    public FinancialManager() {
        generateRandomData(); // Generowanie losowych danych użytkownika
        recordNameHistory(); // Zapisanie początkowego imienia do historii
        recordBalanceHistory(); // Zapisanie początkowego salda do historii
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                // Для Unix-подобных систем
                Runtime.getRuntime().exec("clear");
            }
        }
        catch(Exception e) {
            // В случае ошибки просто добавляем пустые строки
            for(int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    // Metoda generująca losowe dane
    private void generateRandomData() {
        Random random = new Random(); // Obiekt do generowania losowych liczb
        this.balance = 1000 + (random.nextDouble() * 9000); // Losowe saldo w zakresie od 1000 do 10000
        String[] firstNames = {"Anna", "Michael", "John", "Sophia", "Emily"}; // Tablica z możliwymi imionami
        String[] lastNames = {"Smith", "Brown", "Johnson", "Taylor", "Anderson"}; // Tablica z możliwymi nazwiskami
        this.firstName = firstNames[random.nextInt(firstNames.length)]; // Losowy wybór imienia
        this.lastName = lastNames[random.nextInt(lastNames.length)]; // Losowy wybór nazwiska
    }

    // Zapisanie obecnego imienia do historii
    private void recordNameHistory() {
        nameHistory.add(firstName + " " + lastName); // Dodanie pełnego imienia i nazwiska do historii
    }

    // Zapisanie obecnego salda do historii
    private void recordBalanceHistory() {
        balanceHistory.add(balance); // Dodanie obecnego salda do historii
    }

    // Dodanie przychodu
    public void addIncome(double amount, String description) {
        transactions.add(new Transaction("Income", amount, description)); // Dodanie transakcji do listy
        balance += amount; // Aktualizacja salda
        recordBalanceHistory(); // Zapisanie nowego salda do historii
        uniqueDescriptions.add(description); // Dodanie opisu do zbioru unikalnych opisów
        System.out.println("Income added successfully!"); // Informacja o sukcesie
    }

    // Dodanie wydatku
    public void addExpense(double amount, String category, String description) {
        if (amount > balance) { // Sprawdzenie, czy wystarczy środków na wydatek
            System.out.println("Insufficient balance for this expense!"); // Informacja o braku wystarczających środków
        } else {
            transactions.add(new Transaction("Expense", amount, description)); // Dodanie transakcji do listy
            balance -= amount; // Odjęcie kwoty od salda
            recordBalanceHistory(); // Zapisanie nowego salda do historii
            uniqueDescriptions.add(description); // Dodanie opisu do zbioru unikalnych opisów
            categoryExpenses.put(category, categoryExpenses.getOrDefault(category, 0.0) + amount); // Aktualizacja sumy wydatków dla kategorii
            System.out.println("Expense added successfully!"); // Informacja o sukcesie
        }
    }

    // Zmiana imienia użytkownika
    public void changeUserName(String firstName, String lastName) {
        this.firstName = firstName; // Ustawienie nowego imienia
        this.lastName = lastName; // Ustawienie nowego nazwiska
        recordNameHistory(); // Zapisanie zmiany w historii
        System.out.println("User name changed successfully!"); // Informacja o sukcesie
    }

    // Wyświetlenie historii transakcji
    public void viewTransactions() {
        if (transactions.isEmpty()) { // Sprawdzenie, czy lista transakcji jest pusta
            System.out.println("No transactions found!"); // Informacja o braku transakcji
        } else {
            System.out.println("Transaction History:"); // Nagłówek historii transakcji
            for (Transaction t : transactions) { // Iteracja po transakcjach
                System.out.println(t); // Wyświetlenie każdej transakcji
            }
        }
    }

    // Wyświetlenie obecnego salda
    public void viewBalance() {
        System.out.println("Current Balance: " + String.format("%.2f", balance) + " PLN"); // Formatowanie i wyświetlenie salda
    }

    // Wyświetlenie unikalnych opisów transakcji
    public void viewUniqueDescriptions() {
        System.out.println("Unique Descriptions of Transactions:"); // Nagłówek
        for (String desc : uniqueDescriptions) { // Iteracja po unikalnych opisach
            System.out.println("- " + desc); // Wyświetlenie opisu
        }
    }

    // Wyświetlenie wydatków według kategorii
    public void viewCategoryExpenses() {
        System.out.println("Expenses by Category:"); // Nagłówek
        for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) { // Iteracja po kategoriach wydatków
            System.out.println(entry.getKey() + ": " + String.format("%.2f", entry.getValue()) + " PLN"); // Wyświetlenie kategorii i sumy wydatków
        }
    }

    // Wyświetlenie informacji o użytkowniku
    public void viewUserInfo() {
        System.out.println("User: " + firstName + " " + lastName); // Wyświetlenie imienia i nazwiska użytkownika
    }

    // Wyświetlenie historii zmian imienia
    public void viewNameHistory() {
        System.out.println("Name Change History:"); // Nagłówek
        for (String name : nameHistory) { // Iteracja po historii zmian
            System.out.println("- " + name); // Wyświetlenie zmiany
        }
    }

    // Wyświetlenie historii zmian salda
    public void viewBalanceHistory() {
        System.out.println("Balance History:"); // Nagłówek
        for (double bal : balanceHistory) { // Iteracja po historii salda
            System.out.println("- " + String.format("%.2f", bal) + " PLN"); // Wyświetlenie salda
        }
    }

    // Główna metoda programu
    public static void main(String[] args) {
        FinancialManager manager = new FinancialManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        String menu = "Personal Finance Manager\n" +
                "1. Add Income\n" +
                "2. Add Expense\n" +
                "3. View Transactions\n" +
                "4. View Balance\n" +
                "5. View Unique Descriptions\n" +
                "6. View Category Expenses\n" +
                "7. View User Info\n" +
                "8. View Name History\n" +
                "9. View Balance History\n" +
                "10. Change User Name\n" +
                "11. Exit\n";

        clearConsole();
        System.out.println(menu);

        while (running) {
            System.out.print("Choose an option: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        System.out.print("Enter income amount: ");
                        double income = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter description: ");
                        String incomeDesc = scanner.nextLine();
                        manager.addIncome(income, incomeDesc);
                        Thread.sleep(1500);
                        break;

                    case 2:
                        System.out.print("Enter expense amount: ");
                        double expense = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter category: ");
                        String category = scanner.nextLine();
                        System.out.print("Enter description: ");
                        String expenseDesc = scanner.nextLine();
                        manager.addExpense(expense, category, expenseDesc);
                        Thread.sleep(1500);
                        break;

                    case 3:
                        manager.viewTransactions();
                        System.out.print("\nPress Enter to continue...");
                        scanner.nextLine();
                        break;

                    case 4:
                        manager.viewBalance();
                        System.out.print("\nPress Enter to continue...");
                        scanner.nextLine();
                        break;

                    case 5:
                        manager.viewUniqueDescriptions();
                        System.out.print("\nPress Enter to continue...");
                        scanner.nextLine();
                        break;

                    case 6:
                        manager.viewCategoryExpenses();
                        System.out.print("\nPress Enter to continue...");
                        scanner.nextLine();
                        break;

                    case 7:
                        manager.viewUserInfo();
                        System.out.print("\nPress Enter to continue...");
                        scanner.nextLine();
                        break;

                    case 8:
                        manager.viewNameHistory();
                        System.out.print("\nPress Enter to continue...");
                        scanner.nextLine();
                        break;

                    case 9:
                        manager.viewBalanceHistory();
                        System.out.print("\nPress Enter to continue...");
                        scanner.nextLine();
                        break;

                    case 10:
                        System.out.print("Enter new first name: ");
                        String newFirstName = scanner.nextLine();
                        System.out.print("Enter new last name: ");
                        String newLastName = scanner.nextLine();
                        manager.changeUserName(newFirstName, newLastName);
                        Thread.sleep(1500);
                        break;

                    case 11:
                        running = false;
                        System.out.println("Exiting... Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid option! Please try again.");
                        Thread.sleep(1500);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again.");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        scanner.close();
    }
}
