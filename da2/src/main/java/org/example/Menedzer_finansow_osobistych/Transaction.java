package org.example.Menedzer_finansow_osobistych;

import java.util.*; // Importujemy wszystkie klasy z pakietu java.util do użycia list, map i zbiorów

// Klasa Transaction reprezentuje jedną transakcję (przychód lub wydatek)
class Transaction {
    private String type; // Typ transakcji: "Income" (przychód) lub "Expense" (wydatek)
    private double amount; // Kwota transakcji
    private String description; // Opis transakcji

    // Konstruktor do tworzenia nowej transakcji
    public Transaction(String type, double amount, String description) {
        this.type = type; // Przypisanie typu transakcji
        this.amount = amount; // Przypisanie kwoty transakcji
        this.description = description; // Przypisanie opisu transakcji
    }

    // Getter dla typu transakcji
    public String getType() {
        return type;
    }

    // Getter dla kwoty transakcji
    public double getAmount() {
        return amount;
    }

    // Nadpisanie metody toString do wyświetlania informacji o transakcji
    @Override
    public String toString() {
        return type + " - " + amount + " PLN (" + description + ")";
    }
}