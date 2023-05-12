package AuctionMechanism;

import AuctionMechanism.TransactionTypes.Transaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Block {
    private List<Transaction> transactions;
    private long timestamp;
    private String previousBlockHash;
    private String hash;
    private int nonce;
    private User validator;

    public Block(List<Transaction> transactions, long timestamp, String previousBlockHash) {
        this.transactions = transactions;
        this.timestamp = timestamp;
        this.previousBlockHash = previousBlockHash;
        this.nonce = 0;
        this.hash = calculateHash();
    }

    public Block(List<Transaction> transactions, long timestamp, String previousBlockHash, User validator) {
        this.transactions = transactions;
        this.timestamp = timestamp;
        this.previousBlockHash = previousBlockHash;
        this.nonce = 0;
        this.hash = calculateHash();
        this.validator = validator;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public String getHash() {
        return hash;
    }

    public int getNonce() {
        return nonce;
    }

    public void printBlock() {

        System.out.println("INICIO_Block: " + this.toString());
        System.out.println("main.Blockchain.main.Blockchain.Block:Timestamp: " + this.timestamp);
        System.out.println("main.Blockchain.main.Blockchain.Block:PreviousBlockHash " + this.previousBlockHash);
        System.out.println("main.Blockchain.main.Blockchain.Block:Hash " + this.hash);
        System.out.println("main.Blockchain.main.Blockchain.Block:Transactions: ");
        for(Transaction transaction : this.transactions)
        {
            //transaction.printTransaction();
        }
        System.out.println("main.Blockchain.main.Blockchain.Block:FIM_Transactions: ");
        System.out.println("FIM_Block: " + this.toString());
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("main.Blockchain.main.Blockchain.Block mined! Nonce value: " + nonce);
        for (Transaction transaction : this.transactions) {
            //transaction.execute();
            System.out.println("main.Blockchain.Transaction: " + transaction.toString() + " executed!");
            //System.out.println("main.Blockchain.Transaction Amount: " + transaction.getAmount());
        }
    }

    public String calculateHash() {
        String data = previousBlockHash + Long.toString(timestamp) + Integer.toString(nonce) + transactionsToString();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isValidBlock(String previousBlockHash) {
        return this.previousBlockHash.equals(previousBlockHash) && hash.equals(calculateHash());
    }

    private String transactionsToString() {
        StringBuilder sb = new StringBuilder();
        for (Transaction tx : transactions) {
            sb.append(tx.getHash());
        }
        return sb.toString();
    }
}
