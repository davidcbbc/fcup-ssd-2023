package AuctionMechanism.TransactionTypes;

import AuctionMechanism.util.Item;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PublicKey;

public class CloseAuctionTransaction extends Transaction {

    PublicKey winnerPublicKey;
    float winningBid;

    public CloseAuctionTransaction(PublicKey sellerPublicKey, Item auctionedItem, PublicKey winnerPublicKey, float winningBid, byte[] signature) {
        super(sellerPublicKey, auctionedItem);
        this.winnerPublicKey = winnerPublicKey;
        this.winningBid = winningBid;
        this.setHash(this.calculateHash());
    }

    public PublicKey getWinnerPublicKey() {
        return winnerPublicKey;
    }

    public void setWinnerPublicKey(PublicKey winnerPublicKey) {
        this.winnerPublicKey = winnerPublicKey;
    }

    @Override
    public String toString(){
        return "Seller: " + this.getSellerPublicKey() + "\nItem: " + this.getAuctionedItem().getName() + "\nWinner: " + this.winnerPublicKey + "\nWinning Bid: " + this.winningBid;
    }

    @Override
    public byte[] calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = this.getSellerPublicKey()  + this.getAuctionedItem().getName() + this.winnerPublicKey + this.winningBid ;// + this.buyerPublicKey + this.minimumBid + this.currentBid + this.endTime.toString() + this.startTime.toString();
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

    public float getWinningBid() {
        return winningBid;
    }
}
