package AuctionMechanism.util;

public class Item {
    private static int idCounter = 0; // This counter will increment with each new Item
    private int id;
    private String Name;
    private String Description;

    public Item(String Name, String Description){
        this.Name = Name;
        this.Description = Description;
        this.id = idCounter++;
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

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "Item: " + this.Name + "\nDescription: " + this.Description;
    }



}
