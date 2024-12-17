package org.example.Menedzer_finansow_osobistych;

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
        FinancialManager manager = new FinancialManager(); // Tworzenie obiektu managera finansów
        Scanner scanner = new Scanner(System.in); // Tworzenie obiektu Scanner do wprowadzania danych z konsoli
        boolean running = true; // Flaga działania programu

        while (running) { // Główna pętla programu
            System.out.println("\nPersonal Finance Manager"); // Nagłówek menu
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Transactions");
            System.out.println("4. View Balance");
            System.out.println("5. View Unique Descriptions");
            System.out.println("6. View Category Expenses");
            System.out.println("7. View User Info");
            System.out.println("8. View Name History");
            System.out.println("9. View Balance History");
            System.out.println("10. Change User Name");
            System.out.println("11. Exit");
            System.out.print("Choose an option: "); // Prośba o wybór opcji

            int choice = scanner.nextInt(); // Odczytanie wyboru użytkownika
            scanner.nextLine(); // Pobranie znaku nowej linii

            switch (choice) {
                case 1:
                    System.out.print("Enter income amount: "); // Prośba o kwotę przychodu
                    double income = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter description: "); // Prośba o opis przychodu
                    String incomeDesc = scanner.nextLine();
                    manager.addIncome(income, incomeDesc); // Dodanie przychodu
                    break;

                case 2:
                    System.out.print("Enter expense amount: "); // Prośba o kwotę wydatku
                    double expense = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter category: "); // Prośba o kategorię wydatku
                    String category = scanner.nextLine();
                    System.out.print("Enter description: "); // Prośba o opis wydatku
                    String expenseDesc = scanner.nextLine();
                    manager.addExpense(expense, category, expenseDesc); // Dodanie wydatku
                    break;

                case 3:
                    manager.viewTransactions(); // Wyświetlenie historii transakcji
                    break;

                case 4:
                    manager.viewBalance(); // Wyświetlenie salda
                    break;

                case 5:
                    manager.viewUniqueDescriptions(); // Wyświetlenie unikalnych opisów
                    break;

                case 6:
                    manager.viewCategoryExpenses(); // Wyświetlenie wydatków według kategorii
                    break;

                case 7:
                    manager.viewUserInfo(); // Wyświetlenie informacji o użytkowniku
                    break;

                case 8:
                    manager.viewNameHistory(); // Wyświetlenie historii zmian imienia
                    break;

                case 9:
                    manager.viewBalanceHistory(); // Wyświetlenie historii zmian salda
                    break;

                case 10:
                    System.out.print("Enter new first name: "); // Prośba o nowe imię
                    String newFirstName = scanner.nextLine();
                    System.out.print("Enter new last name: "); // Prośba o nowe nazwisko
                    String newLastName = scanner.nextLine();
                    manager.changeUserName(newFirstName, newLastName); // Zmiana imienia użytkownika
                    break;

                case 11:
                    running = false; // Zakończenie działania programu
                    System.out.println("Exiting... Goodbye!"); // Informacja o zakończeniu
                    break;

                default:
                    System.out.println("Invalid option! Please try again."); // Informacja o nieprawidłowej opcji
            }
        }

        scanner.close(); // Zamknięcie obiektu Scanner
    }
}

