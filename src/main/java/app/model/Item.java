package app.model;

public class Item {


    private int id;
    private int quantity ;
    private String item_name;
    private String type;
    private String size;
    private float price;

    public Item(){}

    public Item(int id, int quantity, String item_name, String type, String size, float price){
        this.id = id;
        this.quantity = quantity;
        this.size = size;
        this.price = price;
        this.item_name = item_name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
