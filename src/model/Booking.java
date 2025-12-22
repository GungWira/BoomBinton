/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author gungwira
 */
public class Booking {
    private Integer id;
    private Integer court_id;
    private String name;
    private String phone;
    private Integer time_slot_id;
    private LocalDateTime booking_date;
    private Integer price;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;  
    private String courtName;
    private String startTime;
    private String endTime;
    
    public Booking() {
    }
    
    // method constructor
    public Booking(Integer id, Integer court_id, String name, String phone, Integer time_slot_id, 
                   LocalDateTime booking_date, Integer price, String status, 
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.court_id = court_id;
        this.name = name;
        this.phone = phone;
        this.time_slot_id = time_slot_id;
        this.booking_date = booking_date;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Setters

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCourtName() {
        return courtName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    
    // Getters

    public Integer getId() {
        return id;
    }

    public Integer getCourt_id() {
        return court_id;
    }

    public String getName() {
        return name;
    }
    
    public String getPhone(){
        return phone;
    }

    public Integer getTime_slot_id() {
        return time_slot_id;
    }

    public LocalDateTime getBooking_date() {
        return booking_date;
    }

    public Integer getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    // Setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCourt_id(Integer court_id) {
        this.court_id = court_id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTime_slot_id(Integer time_slot_id) {
        this.time_slot_id = time_slot_id;
    }

    public void setBooking_date(LocalDateTime booking_date) {
        this.booking_date = booking_date;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    
    
}

