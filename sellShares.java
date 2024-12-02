import java.io.*;
import java.util.*;

public class sellShares {

    public static void sellAsset(InvestorProfile.Profile currentProfile) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Sell Menu ---");
            System.out.println("1 -> Sell Stocks");
            System.out.println("2 -> Sell Precious Metals");
            System.out.println("3 -> Sell Currency");
            System.out.println("4 -> Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    sellStock(currentProfile, scanner); // For our selling stock
                    break;
                case 2:
                    sellPreciousMetals(currentProfile, scanner); // For our selling precious metals
                    break;
                case 3:
                    sellCurrency(currentProfile, scanner); // For our selling currency
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 4);
    }

    // For stock process
    private static void sellStock(InvestorProfile.Profile currentProfile, Scanner scanner) {
        String STOCK_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\buy_" + currentProfile.id + ".txt";
        String TRANSACTION_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\transaction_history_stock_" + currentProfile.id + ".txt";
        sellAsset(STOCK_FILE, TRANSACTION_FILE, "Stock", scanner, currentProfile);
    }

    // For precious metals process
    private static void sellPreciousMetals(InvestorProfile.Profile currentProfile, Scanner scanner) {
        String GOLD_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\buy_gold_" + currentProfile.id + ".txt";
        String TRANSACTION_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\transaction_history_gold_" + currentProfile.id + ".txt";
        sellAsset(GOLD_FILE, TRANSACTION_FILE, "Precious Metal", scanner, currentProfile);
    }

    // For currency process
    private static void sellCurrency(InvestorProfile.Profile currentProfile, Scanner scanner) {
        String CURRENCY_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\buy_currency_" + currentProfile.id + ".txt";
        String TRANSACTION_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\transaction_history_currency_" + currentProfile.id + ".txt";
        sellAsset(CURRENCY_FILE, TRANSACTION_FILE, "Currency", scanner, currentProfile);
    }

    // To process the asset sale process (stocks, metals or currency) and log the transaction history
    private static void sellAsset(String filePath, String transactionFile, String assetType, Scanner scanner, InvestorProfile.Profile currentProfile) {
        System.out.print("Enter the name of the " + assetType + " you want to sell: "); // Asset name
        String assetName = scanner.nextLine();

        // Reading existing assets from the file
        Map<String, String[]> assetMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] assetDetails = line.split(",");
                assetMap.put(assetDetails[0], assetDetails);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if the asset exists
        if (assetMap.containsKey(assetName)) {
            String[] existingDetails = assetMap.get(assetName);
            double existingPrice = Double.parseDouble(existingDetails[1]);
            int existingQuantity = Integer.parseInt(existingDetails[2]);
            double existingTotalValue = Double.parseDouble(existingDetails[3]);

            System.out.print("Enter the price of the " + assetType + " you want to sell: "); // Asset price
            double sellPrice = scanner.nextDouble();
            System.out.print("Enter the quantity of the " + assetType + " you want to sell: "); // Asset quantity
            int sellQuantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter the date of the sale (YYYY-MM-DD): "); // Date
            String sellDate = scanner.nextLine();
            double sellTotalValue = sellPrice * sellQuantity; // Total value of selling process
            double status = (sellPrice - existingPrice) * sellQuantity; // Calculates profit or loss

            if (sellQuantity <= 0) { // If the quantity is equal to zero or less
                System.out.println("The quantity cannot be 0 or less.");
            } else if (sellQuantity < existingQuantity) { // If the quantity entered is less than the quantity written in the file
                // Update the quantity and total value
                int newQuantity = existingQuantity - sellQuantity; // Calculates new quantity
                double newTotalValue = existingTotalValue - sellTotalValue; // Calculates new total value
                double newPrice = newTotalValue / newQuantity; // Calculates new price

                assetMap.put(assetName, new String[]{assetName, String.valueOf(newPrice), String.valueOf(newQuantity), String.valueOf(newTotalValue), sellDate});

                // Writes the transaction in the transaction history file
                logTransaction(transactionFile, assetType, assetName, sellPrice, sellQuantity, sellTotalValue, sellDate, status);

                // Display profit/loss information
                if (status > 0) { // If your status is bigger than zero
                    System.out.println("You made a profit of " + status + " from selling " + assetName + ".");
                } else { // If your status is smaller than zero
                    System.out.println("You incurred a loss of " + status + " from selling " + assetName + ".");
                }
            } else if (sellQuantity == existingQuantity) { // If the quantity entered is equal to the quantity written in the file
                // Remove the asset from the portfolio
                assetMap.remove(assetName);
                // Writes the transaction in the transaction history file
                logTransaction(transactionFile, assetType, assetName, sellPrice, sellQuantity, sellTotalValue, sellDate, status);

                if (status > 0) { // If your status is bigger than zero
                    System.out.println("You made a profit of " + status + " from selling " + assetName + ".");
                } else { // If your status is smaller than zero
                    System.out.println("You incurred a loss of " + status + " from selling " + assetName + ".");
                }
            } else { // If the quantity entered is bigger than the quantity written in the file
                System.out.println("The quantity to sell cannot exceed the available quantity.");
            }

            // Write updated asset data back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String[] assetDetails : assetMap.values()) {
                    writer.write(String.join(",", assetDetails));
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else { // If it's not in the file you want to sell
            System.out.println("The " + assetType + " you are trying to sell does not exist in your portfolio.");
        }
    }

    // Writes information in the transaction history file
    private static void logTransaction(String transactionFile, String assetType, String assetName, double assetPrice, int assetQuantity, double totalValue, String assetDate, double status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile, true))) {
            writer.write(assetType + " Sell: " + assetName + "," + assetPrice + "," + assetQuantity + "," + totalValue + "," + assetDate + "," + status);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
