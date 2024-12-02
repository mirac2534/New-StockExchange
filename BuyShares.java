import java.io.*;
import java.util.*;

public class BuyShares {

    // Purchase menu
    public static void buyAsset(InvestorProfile.Profile currentProfile) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Purchase Menu ---");
            System.out.println("1 -> Buy Stocks");
            System.out.println("2 -> Buy Precious Metals");
            System.out.println("3 -> Buy Currency");
            System.out.println("4 -> Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    buyStock(currentProfile, scanner); // For our buying stock
                    break;
                case 2:
                    buyPreciousMetals(currentProfile, scanner); // For our buying precious metals
                    break;
                case 3:
                    buyCurrency(currentProfile, scanner); // For our buying currency
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
    private static void buyStock(InvestorProfile.Profile currentProfile, Scanner scanner) {
        String STOCK_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\buy_" + currentProfile.id + ".txt";
        String TRANSACTION_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\transaction_history_stock_" + currentProfile.id + ".txt";
        buyAsset(STOCK_FILE, TRANSACTION_FILE, "Stock", scanner, currentProfile);
    }

    // For precious metals process
    private static void buyPreciousMetals(InvestorProfile.Profile currentProfile, Scanner scanner) {
        String GOLD_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\buy_gold_" + currentProfile.id + ".txt";
        String TRANSACTION_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\transaction_history_gold_" + currentProfile.id + ".txt";
        buyAsset(GOLD_FILE, TRANSACTION_FILE, "Precious Metal", scanner, currentProfile);
    }

    // For currency process
    private static void buyCurrency(InvestorProfile.Profile currentProfile, Scanner scanner) {
        String CURRENCY_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\buy_currency_" + currentProfile.id + ".txt";
        String TRANSACTION_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\transaction_history_currency_" + currentProfile.id + ".txt";
        buyAsset(CURRENCY_FILE, TRANSACTION_FILE, "Currency", scanner, currentProfile);
    }

    // Requests asset information from the user, saves it to both the portfolio file and the transaction history file
    private static void buyAsset(String filePath, String transactionFile, String assetType, Scanner scanner, InvestorProfile.Profile currentProfile) {
        System.out.print("Enter " + assetType + " Name: "); // Asset name
        String assetName = scanner.nextLine();
        System.out.print("Enter " + assetType + " Price: "); // Asset price
        double assetPrice = scanner.nextDouble();
        System.out.print("Enter " + assetType + " Quantity: "); // Asset quantity
        int assetQuantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Date (YYYY-MM-DD): "); // Date of process
        String date = scanner.nextLine();
        double totalValue = assetPrice * assetQuantity;

        Map<String, String[]> assetMap = new HashMap<>();

        // Reading existing assets from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] assetDetails = line.split(",");
                assetMap.put(assetDetails[0], assetDetails);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If the asset already exists, updates information
        if (assetMap.containsKey(assetName)) {
            String[] existingDetails = assetMap.get(assetName); // After found the asset, puts other information to details array
            double existingPrice = Double.parseDouble(existingDetails[1]);
            int existingQuantity = Integer.parseInt(existingDetails[2]);
            double existingTotalValue = Double.parseDouble(existingDetails[3]);

            // Calculate new price, total value and quantity
            int newQuantity = existingQuantity + assetQuantity;
            double newTotalValue = existingTotalValue + totalValue;
            double newPrice = newTotalValue / newQuantity;
                                                                    // valueOf is for changing to x variable type ( x = int, double, string...)
            assetMap.put(assetName, new String[]{assetName, String.valueOf(newPrice), String.valueOf(newQuantity), String.valueOf(newTotalValue), date});
        } else {
            // Add the new asset if it doesn't exist
            assetMap.put(assetName, new String[]{assetName, String.valueOf(assetPrice), String.valueOf(assetQuantity), String.valueOf(totalValue), date});
        }

        // Writing updated asset data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] assetDetails : assetMap.values()) {
                writer.write(String.join(",", assetDetails));
                writer.newLine();
            }
        } catch (IOException e) { //
            e.printStackTrace();
        }

        // Writes the transaction in the transaction history file
        logTransaction(transactionFile, assetType, assetName, assetPrice, assetQuantity, totalValue, date);
        System.out.println("You purchased " + assetType + " successfully."); // If sell process is successful
    }

    // Writes information in the transaction history file
    private static void logTransaction(String transactionFile, String assetType, String assetName, double assetPrice, int assetQuantity, double totalValue, String date) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile, true))) {
            // Write the transaction details to the file
            writer.write(assetType + " Purchase: " + assetName + "," + assetPrice + "," + assetQuantity + "," + totalValue + "," + date);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
