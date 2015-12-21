package app.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Order {

    @Id
    private String id;
    private String cust_name;
    private ArrayList<Item> items;
    private Float total_price;

    public Order(){}

    public Order(String cust_name, ArrayList<Item> items, Float total_price){
        this.cust_name = cust_name;
        this.items = items;
        this.total_price = total_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Float total_price) {
        this.total_price = total_price;
    }
}
