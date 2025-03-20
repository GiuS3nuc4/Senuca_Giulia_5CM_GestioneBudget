package com.example.senuca_giulia_5cm_gestionebudget;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Viste (componenti UI)
    private EditText budgetAmountEditText;
    private Button setBudgetButton;
    private Button foodButton, transportButton, incomeButton, otherButton, submitButton;
    private LinearLayout transactionFieldsLayout;
    private EditText transactionDescriptionEditText, transactionAmountEditText;
    private TextView budgetInfoTextView, totalExpensesTextView, foodExpensesTextView,
            transportExpensesTextView, otherExpensesTextView, incomeTextView, totalGeneralTextView;

    // Variabili per i dati
    private double initialBudget = 0.00;
    private double foodExpenses = 0.00;
    private double transportExpenses = 0.00;
    private double otherExpenses = 0.00;
    private double income = 0.00;
    private double totalGeneral = 0.00;

    private String selectedCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inizializzazione delle viste
        budgetAmountEditText = findViewById(R.id.budgetAmountEditText);
        setBudgetButton = findViewById(R.id.setBudgetButton);
        foodButton = findViewById(R.id.foodButton);
        transportButton = findViewById(R.id.transportButton);
        incomeButton = findViewById(R.id.incomeButton);
        otherButton = findViewById(R.id.otherButton);
        submitButton = findViewById(R.id.submitButton);
        transactionFieldsLayout = findViewById(R.id.transactionFieldsLayout);
        transactionDescriptionEditText = findViewById(R.id.transactionDescriptionEditText);
        transactionAmountEditText = findViewById(R.id.transactionAmountEditText);
        budgetInfoTextView = findViewById(R.id.budgetInfoTextView);
        totalExpensesTextView = findViewById(R.id.totalExpensesTextView);
        foodExpensesTextView = findViewById(R.id.foodExpensesTextView);
        transportExpensesTextView = findViewById(R.id.transportExpensesTextView);
        otherExpensesTextView = findViewById(R.id.otherExpensesTextView);
        incomeTextView = findViewById(R.id.incomeTextView);
        totalGeneralTextView = findViewById(R.id.totalGeneralTextView);

        // Nasconde i campi di input all'inizio
        transactionFieldsLayout.setVisibility(View.GONE);

        // Imposta il budget iniziale
        setBudgetButton.setOnClickListener(v -> {
            String budgetAmount = budgetAmountEditText.getText().toString();
            if (!budgetAmount.isEmpty()) {
                initialBudget = Double.parseDouble(budgetAmount);
                updateBudgetInfo();
                updateTotalGeneral();
            }
        });

        // Listener per i bottoni delle categorie
        foodButton.setOnClickListener(v -> showTransactionFields("food"));
        transportButton.setOnClickListener(v -> showTransactionFields("transport"));
        incomeButton.setOnClickListener(v -> showTransactionFields("income"));
        otherButton.setOnClickListener(v -> showTransactionFields("other"));

        // Bottone per inviare la transazione
        submitButton.setOnClickListener(v -> addTransaction());
    }

    private void showTransactionFields(String category) {
        selectedCategory = category;
        transactionFieldsLayout.setVisibility(View.VISIBLE);
        transactionDescriptionEditText.setText("");
        transactionAmountEditText.setText("");
    }

    private void addTransaction() {
        String amountStr = transactionAmountEditText.getText().toString();
        if (!amountStr.isEmpty()) {
            double amount = Double.parseDouble(amountStr);

            switch (selectedCategory) {
                case "food":
                    foodExpenses += amount;
                    updateFoodExpenses();
                    break;
                case "transport":
                    transportExpenses += amount;
                    updateTransportExpenses();
                    break;
                case "other":
                    otherExpenses += amount;
                    updateOtherExpenses();
                    break;
                case "income":
                    income += amount;
                    updateIncome();
                    break;
            }

            updateTotalGeneral();
            transactionFieldsLayout.setVisibility(View.GONE); // Nasconde i campi dopo l'invio
        }
    }

    private void updateBudgetInfo() {
        String budgetText = String.format("Budget iniziale: €%.2f", initialBudget);
        budgetInfoTextView.setText(budgetText);
    }

    private void updateFoodExpenses() {
        double percentage = (initialBudget > 0) ? (foodExpenses / initialBudget) * 100 : 0;
        foodExpensesTextView.setText(String.format("Cibo: €%.2f (%.0f%%)", foodExpenses, percentage));
    }

    private void updateTransportExpenses() {
        double percentage = (initialBudget > 0) ? (transportExpenses / initialBudget) * 100 : 0;
        transportExpensesTextView.setText(String.format("Trasporti: €%.2f (%.0f%%)", transportExpenses, percentage));
    }

    private void updateOtherExpenses() {
        double percentage = (initialBudget > 0) ? (otherExpenses / initialBudget) * 100 : 0;
        otherExpensesTextView.setText(String.format("Altro: €%.2f (%.0f%%)", otherExpenses, percentage));
    }

    private void updateIncome() {
        double percentage = (initialBudget > 0) ? (income / initialBudget) * 100 : 0;
        incomeTextView.setText(String.format("Ricavo: €%.2f (%.0f%%)", income, percentage));
    }

    private void updateTotalGeneral() {
        totalGeneral = initialBudget + income - (foodExpenses + transportExpenses + otherExpenses);
        totalGeneralTextView.setText(String.format("Totale Generale: €%.2f", totalGeneral));

        // Debug per verificare i valori aggiornati
        Log.d("DEBUG", "Budget: " + initialBudget + " | Income: " + income +
                " | Spese: " + (foodExpenses + transportExpenses + otherExpenses) +
                " | Totale Generale: " + totalGeneral);
    }
}
