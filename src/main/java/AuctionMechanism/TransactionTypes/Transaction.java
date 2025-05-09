package AuctionMechanism.TransactionTypes;

import AuctionMechanism.util.Item;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Transaction implements Serializable {

    private PublicKey sellerPublicKey;
    private Item auctionedItem;
    private byte[] hash;
    private byte[] signature;

    public Transaction(PublicKey sellerPublicKey, Item auctionedItem) {
        this.sellerPublicKey = sellerPublicKey;
        this.auctionedItem = auctionedItem;
        this.hash = calculateHash();
        this.signature = null;
    }

    public PublicKey getSellerPublicKey() {
        return sellerPublicKey;
    }

    public void setSellerPublicKey(PublicKey sellerPublicKey) {
        this.sellerPublicKey = sellerPublicKey;
    }

    public Item getAuctionedItem() {
        return auctionedItem;
    }

    public void setAuctionedItem(Item auctionedItem) {
        this.auctionedItem = auctionedItem;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public boolean verifySignature() {

        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(this.sellerPublicKey);
            sig.update(this.hash);
            return sig.verify(this.signature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public byte[] calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = this.sellerPublicKey  + this.auctionedItem.getName();// + this.buyerPublicKey + this.minimumBid + this.currentBid + this.endTime.toString() + this.startTime.toString();
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            //System.out.println("main.Blockchain.Transaction:calculateHash:data: " + data + " hashGenerated: " + hexString.toString());
            return hexString.toString().getBytes(StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        return Objects.equals(sellerPublicKey, transaction.sellerPublicKey) &&
                Objects.equals(auctionedItem, transaction.auctionedItem) &&
                Objects.equals(hash, transaction.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerPublicKey, auctionedItem, hash);
    }
}

/*public class Transaction {
    private User sender;
    private User recipient;
    private double amount;
    private String hash;
    // Add object
    // Add previous bid
    private static double rewardAmount = 10;

    public Transaction(User sender, User recipient, double amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.hash = calculateHash();
    }

    public void printTransaction() {

        System.out.println("INICIO_Transaction: " + this.toString());
        System.out.println("main.Blockchain.Transaction:Sender: " + this.sender);
        System.out.println("main.Blockchain.Transaction:Recipient: " + this.recipient);
        System.out.println("main.Blockchain.Transaction:Amount " + this.amount);
        System.out.println("main.Blockchain.Transaction:Hash " + this.hash);
        System.out.println("FIM_Transaction: " + this.toString());

    }

    // Generate a reward transaction for the given recipient
    public static Transaction rewardTransaction(User recipient) {
        User sender = new User("Reward", 999999999); // Use a special sender for reward transaction
        return new Transaction(sender, recipient, rewardAmount);
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public double getAmount() {
        return amount;
    }

    public String getHash() {
        return hash;
    }

    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = this.sender.getAddress() + this.recipient.getAddress() + this.amount;
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            System.out.println("main.Blockchain.Transaction:calculateHash:data: " + data + " hashGenerated: " + hexString.toString());
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isValid() {
        return !sender.getAddress().equals("") && !recipient.getAddress().equals("") && amount > 0;
    }

    public void execute() {
        sender.subtractBalance(amount);
        recipient.addBalance(amount);
    }
}*/

