import java.io.*;
import java.util.Scanner;

public class InvestorProfile {
    private static final String PROFILE_FILE = "C:\\Users\\durma\\Videos\\java\\StockExchange\\profile.txt";

    static class Profile {
        String name; // The investor's name
        String surname; // The investor's surname
        String id; // The investor's ID number
        String portfolioNumber; // The investor's portfolio number
        String password; // The investor's password

        // Constructor for the Profile class. This method is executed when a new profile is created
        public Profile(String name, String surname, String id, String portfolioNumber, String password) {
            this.name = name;
            this.surname = surname;
            this.id = id;
            this.portfolioNumber = portfolioNumber;
            this.password = password;
        }
    }

    // Checks if there is a profile in the file. If the file exists and contains data, it returns true
    public static boolean profileExists() {
        File file = new File(PROFILE_FILE);
        return file.exists() && file.length() > 0;
    }

    // Verifies the username and password in the file and returns the profile if they match
    public static Profile getProfileFromFile(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROFILE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(","); // Splits each line by commas and stores them in the details array
                String profileUsername = details[2]; // Assuming the username is stored in the ID field (adjust if needed)
                String profilePassword = details[4]; // Retrieves the password from the file

                // If the username and password match
                if (profileUsername.equals(username) && profilePassword.equals(password)) {
                    return new Profile(details[0], details[1], details[2], details[3], details[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Returns null if the username and password do not match
    }

    // Checks if the entered username is already in use
    public static boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROFILE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[2].equals(username)) { // Assuming the username is stored in the ID field
                    return true; // Username is already in use
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // To add a profile to the file
    public static void addProfile(Scanner scanner) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROFILE_FILE, true))) {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Surname: ");
            String surname = scanner.nextLine();

            String username;
            // Checks if the username is unique, if not, prompts the user to enter a different username
            do {
                System.out.print("Enter Username: ");
                username = scanner.nextLine();
                if (usernameExists(username)) {
                    System.out.println("Username already in use, please choose a different username.");
                }
            } while (usernameExists(username));

            System.out.print("Enter Portfolio Number: ");
            String portfolioNumber = scanner.nextLine();
            System.out.print("Set Password: ");
            String password = scanner.nextLine();

            // Writes the received information to the file
            writer.write(name + "," + surname + "," + username + "," + portfolioNumber + "," + password);
            writer.newLine(); // Moves to the new line
            System.out.println("Profile added successfully.");
        } catch (IOException e) {
            System.out.println("Could not create a profile.");
        }
    }

    // Displays the profile information
    public static void viewProfile(Profile profile) {
        if (profile != null) { // If there is a profile, show the information
            System.out.println("Name: " + profile.name);
            System.out.println("Surname: " + profile.surname);
            System.out.println("ID: " + profile.id);
            System.out.println("Portfolio Number: " + profile.portfolioNumber);
        } else {
            System.out.println("No profile found."); // If no profile, returns error message
        }
    }

    // This function is called when we want to change the current profile
    public static Profile changeProfile(Scanner scanner, Profile currentProfile) {
        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();
        Profile profile = getProfileFromFile(inputUsername, inputPassword); // Checks the profile with username and password
        if (profile != null) { // If profile is not null
            System.out.println("Profile changed to " + profile.name + " " + profile.surname);
            return profile; // Return the new profile
        } else { // If there is no other profile or wrong credentials entered
            System.out.println("Invalid username or password.");
            return currentProfile;
        }
    }
}
