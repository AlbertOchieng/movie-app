package com.example.albertzkiki.movieapp.model;

/**
 * Created by albertzkiki on 4/4/2018.
 */

public class Order {


    private int ID;
    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Discount;
    private String Price;


    public Order() {
    }

    public Order(String productId, String productName, String quantity, String discount, String price) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Discount = discount;
        Price = price;
    }

    public Order(int ID, String productId, String productName, String quantity, String discount, String price) {
        this.ID = ID;
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Discount = discount;
        Price = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
