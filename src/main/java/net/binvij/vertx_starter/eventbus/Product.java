package net.binvij.vertx_starter.eventbus;

//immutable 
public class Product {

    private Integer Id;
    private String name; 
    private double price; 


    public Product() {

    }

    public Product(Integer id, String name, double price) {
        Id = id;
        this.name = name;
        this.price = price;
    }


    public Integer getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
    	return Id + ";" + name +";" + price;
    }
    
}
