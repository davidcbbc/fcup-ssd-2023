package AuctionMechanism.util;

public class Item {
    String Name;
    String Description;

    public Item(String Name, String Description){
        this.Name = Name;
        this.Description = Description;
    }

    public String getName(){
        return this.Name;
    }

    public String getDescription(){
        return this.Description;
    }

    public void setName(String Name){
        this.Name = Name;
    }

    public void setDescription(String Description){
        this.Description = Description;
    }

    @Override
    public String toString(){
        return "Item: " + this.Name + "\nDescription: " + this.Description;
    }



}
