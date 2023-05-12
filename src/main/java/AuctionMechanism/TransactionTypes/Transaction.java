package AuctionMechanism.TransactionTypes;

import AuctionMechanism.util.Item;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Transaction {

    private PublicKey sellerPublicKey;
    private Item auctionedItem;
    private byte[] hash;
    private byte[] signature;

    public Transaction(PublicKey sellerPublicKey, Item auctionedItem) {
        this.sellerPublicKey = sellerPublicKey;
        //this.buyerPublicKey = null;
        this.auctionedItem = auctionedItem;
        //this.minimumBid = minimumBid;
        //this.currentBid = 0;
        //this.startTime = startTime;
        //this.endTime = endTime;
        //this.status = TransactionStatus.REGISTERED;
        this.hash = calculateHash();
        this.signature = null;
    }

    public Transaction(PublicKey sellerPublicKey, Item auctionedItem,String hash) {
        //this.sellerPublicKey = sellerPublicKey;
        //this.buyerPublicKey = null;
        this.auctionedItem = auctionedItem;
        //this.minimumBid = minimumBid;
        //this.currentBid = 0;
        //this.startTime = startTime;
        //this.endTime = endTime;
        //this.status = TransactionStatus.REGISTERED;
        this.hash = null;
    }

    public PublicKey getSellerPublicKey() {
        return sellerPublicKey;
    }

    public void setSellerPublicKey(PublicKey sellerPublicKey) {
        this.sellerPublicKey = sellerPublicKey;
    }

    /*
    public String getBuyerPublicKey() {
        return buyerPublicKey;
    }

    public void setBuyerPublicKey(String buyerPublicKey) {
        this.buyerPublicKey = buyerPublicKey;
    }
*/
    public Item getAuctionedItem() {
        return auctionedItem;
    }

    public void setAuctionedItem(Item auctionedItem) {
        this.auctionedItem = auctionedItem;
    }
/*
    public int getMinimumBid() {
        return minimumBid;
    }

    public void setMinimumBid(int minimumBid) {
        this.minimumBid = minimumBid;
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
*/
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

    public void sign(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initSign(privateKey);
        rsa.update(getDataToSign());
        this.signature = rsa.sign();
    }

    public byte[] getDataToSign() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(this.sellerPublicKey.getEncoded());
        buffer.put(this.auctionedItem.getName().getBytes());

        return Arrays.copyOfRange(buffer.array(), 0, buffer.position());
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

            System.out.println("main.Blockchain.Transaction:calculateHash:data: " + data + " hashGenerated: " + hexString.toString());
            return hexString.toString().getBytes(StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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

