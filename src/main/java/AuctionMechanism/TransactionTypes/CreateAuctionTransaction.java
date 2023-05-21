package AuctionMechanism.TransactionTypes;

import AuctionMechanism.util.Item;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.util.Arrays;

public class CreateAuctionTransaction extends Transaction implements Serializable {


    private float minimumBid;

    public CreateAuctionTransaction(PublicKey sellerPublicKey, Item auctionedItem,float minimumBid){
        super(sellerPublicKey,auctionedItem);
        this.minimumBid = minimumBid;
        this.setHash(this.calculateHash());

    }

    public float getMinimumBid() {
        return minimumBid;
    }

    public void setMinimumBid(float minimumBid) {
        this.minimumBid = minimumBid;
    }

    @Override
    public String toString(){
        return "Seller: " + this.getSellerPublicKey() + "\nItem: " + this.getAuctionedItem().getName() + "\nDescription: " + this.getAuctionedItem().getDescription() + "\nMinimum Bid: " + this.minimumBid + "\nSignature: " + this.getSignature() + "\nHash: " + this.getHash() + "\n ";
    }

    @Override
    public byte[] calculateHash() {
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
            return hexString.toString().getBytes(StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof CreateAuctionTransaction))
            return false;
        CreateAuctionTransaction t = (CreateAuctionTransaction) o;
        if (Arrays.equals(this.getHash(),t.getHash()) && Arrays.equals(this.getSignature(),t.getSignature()) && this.getSellerPublicKey().equals(t.getSellerPublicKey()) && this.getAuctionedItem().equals(t.getAuctionedItem()) && this.minimumBid == t.minimumBid) {
            return true;
        }
        return false;
    }

}
