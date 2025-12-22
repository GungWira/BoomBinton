/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.TimeSlotDAO;
import java.util.List;
import model.TimeSlot;

/**
 *
 * @author gungwira
 */
public class TimeslotController {
    final private TimeSlotDAO timeSlotDAO;
    
    // method constructor untuk timeslot controller
    public TimeslotController(){
        this.timeSlotDAO = new TimeSlotDAO();
    }
    
    // method untuk mengambil list time slot saat ini
    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotDAO.getAllTimeSlots();
    }
    
    // method untuk membuat court baru
    public boolean createCourt(String startTime, String endTime){
        return timeSlotDAO.createTimeSlot(startTime, endTime);
    }
    
    // method untuk menyimpan perubahan data court
    public boolean editCourt(Integer id, String startTime, String endTime){
        return timeSlotDAO.editTimeSlot(id, startTime, endTime);
    }
    
    // method untuk menghapus data court
    public boolean deleteCourt(Integer id){
        return timeSlotDAO.deleteTimeSlot(id);
    }
}
