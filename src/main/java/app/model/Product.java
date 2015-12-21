package app.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Product {

    @Id
    private String id;
    private String name;
    private String type;
    private ArrayList<String> size;
    private Price price;



    public Product(){}

    public Product(String name, String type, ArrayList<String> size, Price price){
        this.name = name;
        this.type = type;
        this.size = size;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getSize() {
        return size;
    }

    public void setSize(ArrayList<String> size) {
        this.size = size;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
