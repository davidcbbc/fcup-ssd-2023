package AuctionMechanism.TransactionTypes;

import AuctionMechanism.util.Item;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;

public class BidAuctionTransaction extends Transaction{

    private int bidAmount;
    private PublicKey buyerPublicKey;

    public BidAuctionTransaction(PublicKey sellerPublicKey, PublicKey buyerPublicKey, Item auctionedItem, int bidAmount, byte[] signature){
        super(sellerPublicKey,auctionedItem);
        this.buyerPublicKey = buyerPublicKey;
        this.bidAmount = bidAmount;
        this.setHash(this.calculateHash());
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }

    public PublicKey getBuyerPublicKey(){
        return this.buyerPublicKey;
    }

    public void setBuyerPublicKey(PublicKey buyerPublicKey){
        this.buyerPublicKey = buyerPublicKey;
    }

    @Override
    public String toString(){
        return "Seller: " + this.getSellerPublicKey() + "\nItem: " + this.getAuctionedItem().getName() + "\nDescription: " + this.getAuctionedItem().getDescription() + "\nBid Amount: " + this.bidAmount + "\nBuyer: " + this.buyerPublicKey ;
    }

    @Override
    public byte[] calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = this.getSellerPublicKey()  + this.getAuctionedItem().getName() + this.buyerPublicKey + this.bidAmount;// + this.buyerPublicKey + this.minimumBid + this.currentBid + this.endTime.toString() + this.startTime.toString();
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


    @Override
    public boolean verifySignature() {

        try {
            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(this.buyerPublicKey);
            sig.update(this.getSignature());
            return sig.verify(this.getSignature());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


