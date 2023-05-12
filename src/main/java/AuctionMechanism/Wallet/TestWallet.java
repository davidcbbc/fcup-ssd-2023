package AuctionMechanism.Wallet;

import AuctionMechanism.Wallet.Wallet;

public class TestWallet {
    public static void main(String[] args) {
        // Create a new wallet and save it to a file
        Wallet alice = new Wallet();
        String filePath = "alice_wallet.p12";
        String passphrase = "my_secret_passphrase";

        try {
            alice.saveToFile(filePath, passphrase);
            System.out.println("Alice's wallet saved to " + filePath);
        } catch (Exception e) {
            System.err.println("Error saving wallet: " + e.getMessage());
        }

        // Load a wallet from a saved file
        Wallet loadedWallet = null;
        try {
            loadedWallet = Wallet.loadFromFile(filePath, passphrase);
            System.out.println("Loaded wallet from " + filePath);
        } catch (Exception e) {
            System.err.println("Error loading wallet: " + e.getMessage());
        }

        // Check if the loaded wallet's public key matches the original wallet's public key
        if (loadedWallet != null && loadedWallet.getPublicKey().equals(alice.getPublicKey())) {
            System.out.println("Wallet loaded successfully!");
        } else {
            System.out.println("Failed to load wallet!");
        }

        // Use the loaded wallet to interact with the blockchain
        // ...
    }
}


