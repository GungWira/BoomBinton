/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gungwira
 */
public abstract class ExtraItem implements OrderItem{
    protected String name;
    protected int price;
    protected int quantity;

    // method constructor untuk court
    public ExtraItem(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public int getSubtotal() {
        return price * quantity;
    }
    
    @Override
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}
