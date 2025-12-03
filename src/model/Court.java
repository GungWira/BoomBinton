/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gungwira
 */
public class Court {
    private Integer id;
    private String name;
    private String status;
    private Integer price_per_hour;
    
    public Court(Integer id, String name, String status, Integer price_per_hour){
        this.id = id;
        this.name = name;
        this.status = status;
        this.price_per_hour = price_per_hour;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPrice_per_hour() {
        return price_per_hour;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice_per_hour(Integer price_per_hour) {
        this.price_per_hour = price_per_hour;
    }
    
    
}
