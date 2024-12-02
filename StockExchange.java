import java.util.InputMismatchException;
import java.util.Scanner;

public class StockExchange {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean profileExists = InvestorProfile.profileExists();  // Checks if there is any profile in the file

        if (!profileExists) { // If there is no profile, prompt to create a new profile
            System.out.println("No profiles found. Please create a new profile.");
            InvestorProfile.addProfile(scanner);  // Adding a new profile
        }

        // Introduction part
        System.out.println("Welcome to the investment app");
        System.out.print("Enter your username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine();

        InvestorProfile.Profile currentProfile = InvestorProfile.getProfileFromFile(inputUsername, inputPassword); // Check profile with username and password
        if (currentProfile != null) {
            System.out.println("Login successful!");
            System.out.println("Welcome " + currentProfile.name);
            int option;
            do {
                option = menu(scanner, currentProfile); // Passing scanner and currentProfile to display name in the menu
                switch (option) {
                    case 1:
                        currentProfile = profileMenu(scanner, currentProfile);  // Profile menu actions
                        break;
                    case 2:
                        BuyShares.buyAsset(currentProfile); // Buying shares
                        break;
                    case 3:
                        sellShares.sellAsset(currentProfile); // Selling shares
                        break;
                    case 4:
                        showPortfolio.showAllInvestments(currentProfile); // Viewing portfolio
                        break;
                    case 5:
                        showTransaction.showAllTransactions(currentProfile); // Viewing transaction history
                        break;
                    case 6:
                        System.out.println("Exiting..."); // Exiting the program
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } while (option != 6);

        } else {
            System.out.println("Invalid username or password!");
        }
        scanner.close();
    }

    // Main menu
    public static int menu(Scanner scanner, InvestorProfile.Profile currentProfile) {
        int option = -1;
        while (option == -1) {
            try {
                System.out.println("\n--- Main Menu ---");
                System.out.println("Current Profile: " + currentProfile.name + " " + currentProfile.surname); // Shows current profile
                System.out.println("Enter your choice:");
                System.out.println("1 -> Profile menu");
                System.out.println("2 -> Buy shares");
                System.out.println("3 -> Sell shares");
                System.out.println("4 -> Show the portfolio");
                System.out.println("5 -> Show the transaction history");
                System.out.println("6 -> Exit");
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
                option = -1; // Reset option to prompt for input again
            }
        }
        return option;
    }

    // Profile menu (add/change/view profile)
    public static InvestorProfile.Profile profileMenu(Scanner scanner, InvestorProfile.Profile currentProfile) {
        int option;
        do {
            System.out.println("\n--- Profile Menu ---");
            System.out.println("Current Profile: " + currentProfile.name + " " + currentProfile.surname); // Shows current profile
            System.out.println("1 -> View your profile");
            System.out.println("2 -> Change profile");
            System.out.println("3 -> Add a new profile");
            System.out.println("4 -> Back to Main Menu");
            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (option) {
                    case 1:
                        InvestorProfile.viewProfile(currentProfile); // View current profile
                        break;
                    case 2:
                        currentProfile = InvestorProfile.changeProfile(scanner, currentProfile); // Change current profile
                        break;
                    case 3:
                        System.out.println("Adding a new profile:");
                        InvestorProfile.addProfile(scanner); // Add a new profile
                        break;
                    case 4:
                        System.out.println("Returning to Main Menu...");
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
                option = -1; // Reset option to prompt for input again
            }
        } while (option != 4);

        return currentProfile; // Return the updated profile (if changed)
    }
}
