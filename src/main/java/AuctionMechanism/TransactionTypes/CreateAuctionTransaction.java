package AuctionMechanism.TransactionTypes;

import AuctionMechanism.TransactionTypes.Transaction;
import AuctionMechanism.util.Item;

import java.security.MessageDigest;

public class CreateAuctionTransaction extends Transaction {


    private int minimumBid;

    public CreateAuctionTransaction(String sellerPublicKey, Item auctionedItem, int minimumBid){
        super(sellerPublicKey,auctionedItem);
        this.minimumBid = minimumBid;
        this.setHash(this.calculateHash());
    }

    public int getMinimumBid() {
        return minimumBid;
    }

    public void setMinimumBid(int minimumBid) {
        this.minimumBid = minimumBid;
    }

    @Override
    public String toString(){
        return "Seller: " + this.getSellerPublicKey() + "\nItem: " + this.getAuctionedItem().getName() + "\nDescription: " + this.getAuctionedItem().getDescription() + "\nMinimum Bid: " + this.minimumBid;
    }

    @Override
    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = this.getSellerPublicKey()  + this.getAuctionedItem().getName() + this.minimumBid;// + this.buyerPublicKey + this.minimumBid + this.currentBid + this.endTime.toString() + this.startTime.toString();
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






}
