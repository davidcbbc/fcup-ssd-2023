package AuctionMechanism.util;

import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                Objects.equals(Name, item.Name) &&
                Objects.equals(Description, item.Description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Description, id);
    }

    @Override
    public String toString(){
        return "Item: " + this.Name + "\nDescription: " + this.Description;
    }



}
