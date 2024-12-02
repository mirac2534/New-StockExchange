import java.io.*;
import java.util.*;

public class showPortfolio {

    public static void showAllInvestments(InvestorProfile.Profile currentProfile) {
        String STOCK_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\buy_" + currentProfile.id + ".txt";
        String GOLD_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\buy_gold_" + currentProfile.id + ".txt";
        String CURRENCY_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\buy_currency_" + currentProfile.id + ".txt";

        double totalPortfolioValue = 0.0;
        double totalStocksValue = 0.0;
        double totalGoldValue = 0.0;
        double totalCurrencyValue = 0.0;

        // TreeMaps to store the assets of each type
        TreeMap<Double, List<String[]>> stockMap = new TreeMap<>(Collections.reverseOrder());
        TreeMap<Double, List<String[]>> goldMap = new TreeMap<>(Collections.reverseOrder());
        TreeMap<Double, List<String[]>> currencyMap = new TreeMap<>(Collections.reverseOrder());

        // Reads stocks
        totalStocksValue = readAssetsFromFile(STOCK_FILE, stockMap);

        // Reads precious metals
        totalGoldValue = readAssetsFromFile(GOLD_FILE, goldMap);

        // Reads currency
        totalCurrencyValue = readAssetsFromFile(CURRENCY_FILE, currencyMap);

        // Calculates total portfolio value
        totalPortfolioValue = totalStocksValue + totalGoldValue + totalCurrencyValue;

        // Prints total portfolio value
        System.out.printf("Total Portfolio Value: %.2f\n", totalPortfolioValue);

        // Displays all portfolio
        displayAssetSummary("Stocks", totalStocksValue, totalPortfolioValue, stockMap);
        displayAssetSummary("Precious Metals", totalGoldValue, totalPortfolioValue, goldMap);
        displayAssetSummary("Currency", totalCurrencyValue, totalPortfolioValue, currencyMap);
    }

    // To read assets from a file and calculate the total value
    private static double readAssetsFromFile(String filePath, TreeMap<Double, List<String[]>> assetMap) {
        double totalValue = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] assetDetails = line.split(",");
                String assetName = assetDetails[0];
                double assetPrice = Double.parseDouble(assetDetails[1]);
                int assetQuantity = Integer.parseInt(assetDetails[2]);
                double assetTotalValue = Double.parseDouble(assetDetails[3]);
                String date = assetDetails[4];

                // Add to total value
                totalValue += assetTotalValue;

                // Add asset to the TreeMap, sorted by total value
                assetMap.computeIfAbsent(assetTotalValue, k -> new ArrayList<>()).add(assetDetails);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalValue;
    }

    // To view the asset summary along with the total value and percentage of the portfolio
    private static void displayAssetSummary(String assetType, double assetTotalValue, double totalPortfolioValue, TreeMap<Double, List<String[]>> assetMap) {
        // Calculates percentage of the portfolio for this asset type
        double percentageOfPortfolio = (assetTotalValue / totalPortfolioValue) * 100;

        // Prints the total value and percentage
        System.out.printf("\n%s Total Value: %.2f (%.2f%% of portfolio)\n", assetType, assetTotalValue, percentageOfPortfolio);

        // Prints the sorted assets by total value
        for (Map.Entry<Double, List<String[]>> entry : assetMap.entrySet()) {
            for (String[] assetDetails : entry.getValue()) {
                String assetName = assetDetails[0];
                double assetPrice = Double.parseDouble(assetDetails[1]);
                int assetQuantity = Integer.parseInt(assetDetails[2]);
                double totalValue = Double.parseDouble(assetDetails[3]);
                String date = assetDetails[4];

                // Displays asset information
                System.out.printf("Asset: %s | Price: %.2f | Quantity: %d | Total Value: %.2f | Date: %s\n",
                        assetName, assetPrice, assetQuantity, totalValue, date);
            }
        }
    }
}
