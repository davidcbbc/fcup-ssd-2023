package AuctionMechanism.TransactionTypes;

import AuctionMechanism.util.Item;

public class BidAuctionTransaction extends Transaction{

    private int bidAmount;
    private String buyerPublicKey;

    public BidAuctionTransaction(String sellerPublicKey, String buyerPublicKey, Item auctionedItem, int bidAmount){
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

    public String getBuyerPublicKey(){
        return this.buyerPublicKey;
    }

    public void setBuyerPublicKey(String buyerPublicKey){
        this.buyerPublicKey = buyerPublicKey;
    }

    @Override
    public String toString(){
        return "Seller: " + this.getSellerPublicKey() + "\nItem: " + this.getAuctionedItem().getName() + "\nDescription: " + this.getAuctionedItem().getDescription() + "\nBid Amount: " + this.bidAmount + "\nBuyer: " + this.buyerPublicKey ;
    }

    @Override
    public String calculateHash() {
        try {
            String data = this.getSellerPublicKey()  + this.getAuctionedItem().getName() + this.bidAmount + this.buyerPublicKey ; // + this.buyerPublicKey + this.minimumBid + this.currentBid + this.endTime.toString() + this.startTime.toString();
            return data;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
