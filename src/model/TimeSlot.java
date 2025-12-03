/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gungwira
 */
public class TimeSlot {
    private Integer id;
    private String start_time;
    private String end_time;

    public TimeSlot(Integer id, String start_time, String end_time) {
        this.id = id;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }
    
    // Setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
    
}
