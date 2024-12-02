import java.io.*;
import java.util.*;

public class showTransaction {

    public static void showAllTransactions(InvestorProfile.Profile currentProfile) {
        String STOCK_TRANSACTIONS = "C:\\Users\\durma\\Videos\\java\\StockExchange\\transaction_history_stock_" + currentProfile.id + ".txt";
        String GOLD_TRANSACTIONS = "C:\\Users\\durma\\Videos\\java\\StockExchange\\transaction_history_gold_" + currentProfile.id + ".txt";
        String CURRENCY_TRANSACTIONS = "C:\\Users\\durma\\Videos\\java\\StockExchange\\transaction_history_currency_" + currentProfile.id + ".txt";

        // TreeMap to store transactions sorted by asset type and action (Buy/Sell)
        Map<String, List<String[]>> stockBuyTransactions = new LinkedHashMap<>();
        Map<String, List<String[]>> stockSellTransactions = new LinkedHashMap<>();
        Map<String, List<String[]>> goldBuyTransactions = new LinkedHashMap<>();
        Map<String, List<String[]>> goldSellTransactions = new LinkedHashMap<>();
        Map<String, List<String[]>> currencyBuyTransactions = new LinkedHashMap<>();
        Map<String, List<String[]>> currencySellTransactions = new LinkedHashMap<>();

        // Read transactions from stock, gold, and currency files
        readTransactionsFromFile(STOCK_TRANSACTIONS, "Stock", stockBuyTransactions, stockSellTransactions);
        readTransactionsFromFile(GOLD_TRANSACTIONS, "Precious Metal", goldBuyTransactions, goldSellTransactions);
        readTransactionsFromFile(CURRENCY_TRANSACTIONS, "Currency", currencyBuyTransactions, currencySellTransactions);

        // Display the transactions grouped by type and action
        System.out.println("\n--- All Transactions ---\n");

        // Display all transaction history
        displayGroupedTransactions("Stocks", stockBuyTransactions, stockSellTransactions);
        displayGroupedTransactions("Precious Metals", goldBuyTransactions, goldSellTransactions);
        displayGroupedTransactions("Currency", currencyBuyTransactions, currencySellTransactions);
    }

    // To view transactions grouped by asset type and action
    private static void displayGroupedTransactions(String assetType, Map<String, List<String[]>> buyTransactions, Map<String, List<String[]>> sellTransactions) {
        System.out.println("\n" + assetType + ":");

        // Display Buy Transactions
        System.out.println("  Buy:");
        if (buyTransactions.isEmpty()) {
            System.out.println("    No buy transactions found.");
        } else {
            for (String[] transaction : buyTransactions.values().stream().flatMap(List::stream).toList()) {
                displayTransactionDetails(transaction);
            }
        }

        // Display Sell Transactions
        System.out.println("  Sell:");
        if (sellTransactions.isEmpty()) {
            System.out.println("    No sell transactions found.");
        } else {
            for (String[] transaction : sellTransactions.values().stream().flatMap(List::stream).toList()) {
                displayTransactionDetails(transaction);
            }
        }
    }

    // To view the transaction history
    private static void displayTransactionDetails(String[] transactionDetails) {
        String assetName = transactionDetails[0];
        double price = Double.parseDouble(transactionDetails[1]);
        int quantity = Integer.parseInt(transactionDetails[2]);
        double totalValue = Double.parseDouble(transactionDetails[3]);
        String date = transactionDetails[4];

        // Display transaction in a well-formatted way
        System.out.printf("    Asset: %-10s | Price: %.2f | Quantity: %-4d | Total: %.2f | Date: %s\n",
                assetName, price, quantity, totalValue, date);
    }

    // To read transactions from a file and group them by Buy/Sell Transactions
    private static void readTransactionsFromFile(String filePath, String assetType, Map<String, List<String[]>> buyTransactionsMap, Map<String, List<String[]>> sellTransactionsMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Split by the first colon ":" to separate the action (Buy/Sell) and the details
                String[] actionAndDetails = line.split(": ");
                if (actionAndDetails.length == 2) {
                    String action = actionAndDetails[0].trim(); // Extract "Buy", "Sell" or "Purchase"
                    String[] details = actionAndDetails[1].split(","); // Split the remaining details by comma

                    if (details.length >= 5) {
                        String[] transactionDetails = new String[]{details[0], details[1], details[2], details[3], details[4]}; // [name, price, quantity, total, date]

                        // Add the transaction to the correct map based on action (Buy or Sell)
                        if (action.contains("Buy") || action.contains("Purchase")) {  // Adjust to handle "Purchase" as Buy
                            buyTransactionsMap.computeIfAbsent(assetType, k -> new ArrayList<>()).add(transactionDetails);
                        } else if (action.contains("Sell")) {
                            sellTransactionsMap.computeIfAbsent(assetType, k -> new ArrayList<>()).add(transactionDetails);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
