# Stock Exchange project
The aim of this project is to make people follow their own portfolios easily. Creates individual passbooks for each created profile and categorizes them.
## 1- StockExchange Class

### 1-1- Main Function

Initially, it checks if there is a saved profile. If no profile exists, it prompts the user to create one. Afterward, it moves to the main entry point, asking for the username and password. If a profile is already created, it asks for the username and password. Upon successful entry, the user is directed to the main menu.

### 1-2- Main Menu Function

The main menu provides options about the application's features. Options include Investor Profile, Purchase Operations, Sell Operations, Viewing General Portfolio, and Displaying Transaction History.

### 1-3- Investor Profile Menu Function

If the investor profile menu is selected in the main menu, it directs the user here. Options include viewing the profile, adding a new profile, switching to another profile if available, and returning to the main menu.

## 2- InvestorProfile Class

### 2-1- Profile Inner Class

Represents an individual investor's profile.  
Fields:  
- name (String): Investor's name  
- surname (String): Investor's surname  
- id (String): Investor's ID or username  
- portfolioNumber (String): Portfolio number of the investor  
- password (String): Investor's password  
The constructor initializes the profile instance with the specified fields.

### 2-2- profileExists Method

public static boolean profileExists()  
This method checks if the profile file ('profile.txt') exists and if it contains data. Returns true if a profile exists; otherwise, returns false.

### 2-3- getProfileFromFile Method

public static Profile getProfileFromFile(String username, String password)  
This method validates the provided username and password against the profiles stored in the file. If a matching profile is found, it returns a Profile object; otherwise, it returns null.

### 2-4- usernameExists Method

public static boolean usernameExists(String username)  
Checks if the specified username already exists in the profile file. Returns true if the username is taken; otherwise, returns false.

### 2-5- addProfile Method

public static void addProfile(Scanner scanner)  
Prompts the user to enter profile information, including name, surname, username, portfolio number, and password. Ensures the username is unique and writes the profile information to the profile file.

### 2-6- viewProfile Method

public static void viewProfile(Profile profile)  
Displays the details of the provided profile, such as name, surname, ID, and portfolio number.

### 2-7- changeProfile Method

public static Profile changeProfile(Scanner scanner, Profile currentProfile)  
Allows the user to change to another profile by providing a new username and password. If the credentials are valid, it switches to the new profile; otherwise, retains the current profile.

## 3- BuyShares Class

### 3-1- buyAsset Method

public static void buyAsset(InvestorProfile.Profile currentProfile)  
Provides a menu for users to select the type of asset they wish to purchase (stocks, precious metals, or currency). Calls the respective methods for each asset type.

### 3-2- buyStock Method

private static void buyStock(InvestorProfile.Profile currentProfile, Scanner scanner)  
Handles the purchase of stocks for the user and delegates the process to the buyAssetHelper method.

### 3-3- buyPreciousMetals Method

private static void buyPreciousMetals(InvestorProfile.Profile currentProfile, Scanner scanner)  
Handles the purchase of precious metals (e.g., gold) for the user and delegates the process to the buyAssetHelper method.

### 3-4- buyCurrency Method

private static void buyCurrency(InvestorProfile.Profile currentProfile, Scanner scanner)  
Handles the purchase of currency for the user and delegates the process to the buyAssetHelper method.

### 3-5- buyAssetHelper Method

private static void buyAssetHelper(String filePath, String transactionFile, String assetType, Scanner scanner, InvestorProfile.Profile currentProfile)  
Prompts the user for asset details (name, price, quantity, and date), updates or adds the asset to the user's portfolio file, and logs the transaction.

### 3-6- logTransaction Method

private static void logTransaction(String transactionFile, String assetType, String assetName, double assetPrice, int assetQuantity, double totalValue, String date)  
Logs the transaction details in the specified transaction history file.

## 4- sellShares Class

### 4-1- sellAsset Method

public static void sellAsset(InvestorProfile.Profile currentProfile)  
Provides a menu for users to select the type of asset they wish to sell (stocks, precious metals, or currency). Calls the respective methods for each asset type.

### 4-2- sellStock Method

private static void sellStock(InvestorProfile.Profile currentProfile, Scanner scanner)  
Handles the sale of stocks for the user and delegates the process to the sellAssetHelper method.

### 4-3- sellPreciousMetals Method

private static void sellPreciousMetals(InvestorProfile.Profile currentProfile, Scanner scanner)  
Handles the sale of precious metals (e.g., gold) for the user and delegates the process to the sellAssetHelper method.

### 4-4- sellCurrency Method

private static void sellCurrency(InvestorProfile.Profile currentProfile, Scanner scanner)  
Handles the sale of currency for the user and delegates the process to the sellAssetHelper method.

### 4-5- sellAssetHelper Method

private static void sellAssetHelper(String filePath, String transactionFile, String assetType, Scanner scanner, InvestorProfile.Profile currentProfile)  
Handles the sale process by updating or removing assets from the user's portfolio based on the specified details.

### 4-6- logTransaction Method

private static void logTransaction(String transactionFile, String assetType, String assetName, double assetPrice, int assetQuantity, double totalValue, String assetDate, double status)  
Logs the sale transaction details, including profit or loss, in the specified transaction history file.

## 5- showPortfolio Class

### 5-1- showAllInvestments Method

public static void showAllInvestments(InvestorProfile.Profile currentProfile)  
Displays the user's complete portfolio of assets (stocks, precious metals, and currency) and calculates the total portfolio value.

### 5-2- readAssetsFromFile Method

private static double readAssetsFromFile(String filePath, TreeMap<Double, List<String[]>> assetMap)  
Reads assets from the specified file and calculates their total value, storing them in a TreeMap sorted by total value.

### 5-3- displayAssetSummary Method

private static void displayAssetSummary(String assetType, double assetTotalValue, double totalPortfolioValue, TreeMap<Double, List<String[]>> assetMap)  
Displays a summary of the specified asset type, including its total value and percentage of the total portfolio.

## 6- showTransaction Class

### 6-1- showAllTransactions Method

public static void showAllTransactions(InvestorProfile.Profile currentProfile)  
Displays all transactions for the user's assets, grouped by asset type (stocks, precious metals, and currency) and further categorized into buy and sell transactions.

### 6-2- displayGroupedTransactions Method

private static void displayGroupedTransactions(String assetType, Map<String, List<String[]>> buyTransactions, Map<String, List<String[]>> sellTransactions)  
Displays buy and sell transactions for a given asset type, grouped by the transaction action.

### 6-3- displayTransactionDetails Method

private static void displayTransactionDetails(String[] transactionDetails)  
Displays the details of a single transaction, including the asset name, price, quantity, total value, and date.

### 6-4- readTransactionsFromFile Method

private static void readTransactionsFromFile(String filePath, String assetType, Map<String, List<String[]>> buyTransactionsMap, Map<String, List<String[]>> sellTransactionsMap)  
Reads transaction history from the specified file and groups transactions into buy and sell categories.

