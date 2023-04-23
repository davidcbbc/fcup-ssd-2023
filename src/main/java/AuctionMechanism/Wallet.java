package AuctionMechanism;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class Wallet {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private double balance;

    public Wallet() {
        generateKeyPair();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double newBalance) {
        balance = newBalance;
    }

    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Transaction sendFunds(Wallet recipient, double amount) {
        if (balance < amount) {
            System.out.println("Not enough funds to send transaction.");
            return null;
        }

        Transaction transaction = new Transaction(getPublicKey(), recipient.getPublicKey(), amount);
        transaction.generateSignature(privateKey);

        setBalance(balance - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        return transaction;
    }
}
