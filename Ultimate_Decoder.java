import java.util.Scanner;

public class Ultimate_Decoder {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Ultimate Decoder!");

        // Menu for selecting decoding option
        System.out.println("Choose an option:");
        System.out.println("1. Caesar Cipher");
        System.out.println("2. Atbash Cipher");
        System.out.println("3. Brute Force All");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline

        // Handle user choice
        switch (choice) {
            case 1:
                CaesarCipher(sc);
                break;
            case 2:
                AtbashCipher(sc);
                break;
            case 3:
                BruteForceAll(sc);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Caesar Cipher decoding function
    public static void CaesarCipher(Scanner sc) {
        System.out.print("Do you have a key on how many letters to shift? (y/n): ");
        String key = sc.nextLine();
        String s;

        if (key.equals("y")) {
            // Use user-provided shift key
            System.out.print("Enter the key: ");
            int k = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.print("Enter the string to be decoded: ");
            s = sc.nextLine();
            
            System.out.println("Decoded message: " + decodeCaesar(s, k));
        } else {
            // Try all possible Caesar shifts (brute force)
            System.out.print("Enter the string to be decoded: ");
            s = sc.nextLine();
            for (int i = 0; i < 26; i++) {
                System.out.println("Key " + i + ": " + decodeCaesar(s, i));
            }
        }
    }

    // Caesar Cipher logic
    public static String decodeCaesar(String s, int k) {
        StringBuilder decoded = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c - base - k + 26) % 26 + base);
            }
            decoded.append(c);
        }
        return decoded.toString();
    }

    // Atbash Cipher decoding
    public static void AtbashCipher(Scanner sc) {
        System.out.print("Enter the string to decode with Atbash: ");
        String s = sc.nextLine();
        StringBuilder decoded = new StringBuilder();

        // Atbash: Mirror letter positions
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) (base + ('Z' - Character.toUpperCase(c)));
                if (Character.isLowerCase(base)) c = Character.toLowerCase(c);
            }
            decoded.append(c);
        }

        System.out.println("Decoded with Atbash: " + decoded);
    }

    // Brute force decoding using all known methods
    public static void BruteForceAll(Scanner sc) {
        System.out.print("Enter the string to decode using brute force methods: ");
        String s = sc.nextLine();

        // Try all Caesar shifts
        System.out.println("--- Caesar Cipher Brute Force ---");
        for (int i = 0; i < 26; i++) {
            System.out.println("Key " + i + ": " + decodeCaesar(s, i));
        }

        // Try Atbash
        System.out.println("--- Atbash Cipher ---");
        StringBuilder atbash = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) (base + ('Z' - Character.toUpperCase(c)));
                if (Character.isLowerCase(base)) c = Character.toLowerCase(c);
            }
            atbash.append(c);
        }
        System.out.println("Atbash: " + atbash);
    }
}