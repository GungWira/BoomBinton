/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.BookingDAO;
import dao.CourtDAO;
import dao.TimeSlotDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Court;
import model.TimeSlot;

/**
 *
 * @author gungwira
 */
public class CourtController {
    final private CourtDAO courtDAO;
    final private TimeSlotDAO timeSlotDAO;
    final private BookingDAO bookingDAO;
    final private List<TimeSlot> selectedTimeSlots;
    
    // method constructor untuk court controller
    public CourtController(){
        this.courtDAO = new CourtDAO();
        this.timeSlotDAO = new TimeSlotDAO();
        this.bookingDAO = new BookingDAO();
        this.selectedTimeSlots = new ArrayList<>();
    }
    
    // method untuk mengambil semua data lapangan saat ini
    public List<Court> getAllCourts() {
        try {
            return courtDAO.getAllCourts();
        } catch (Exception e) {
            System.err.println("Error di CourtController.getAllCourts: " + e.getMessage());
            return null;
        }
    }
    
    // method untuk mengambil semua data lapangan active saat ini
    public List<Court> getAllActiveCourts() {
        try {
            return courtDAO.getAllActiveCourts();
        } catch (Exception e) {
            System.err.println("Error di CourtController.getAllCourts: " + e.getMessage());
            return null;
        }
    }
    
    // method untuk mengambil data lapangan spesifik berdasarkan id lapangan
    public Court getCourtById(Integer id){
        try {
            return courtDAO.getCourtById(id);
        } catch (Exception e) {
            System.err.println("Error di CourtController.getCourtById: " + e.getMessage());
            return null;
        }
    }
    
    // method untuk mengambil list time slot saat ini
    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotDAO.getAllTimeSlots();
    }

    // method untuk mengambil data timeslot yang sudah dibooking pada suatu court dan tanggal hari ini
    public List<Integer> getBookedTimeSlotIds(int courtId, LocalDate date) {
        return bookingDAO.getBookedTimeSlotIds(courtId, date);
    }
    
    // method untuk membuat court baru
    public boolean createCourt(String name, Integer price, String status){
        return courtDAO.createCourt(name, price, status);
    }
    
    // method untuk menyimpan perubahan data court
    public boolean editCourt(Integer id, String name, Integer price, String status){
        return courtDAO.editCourt(id, name, price, status);
    }
    
    // method untuk menghapus data court
    public boolean deleteCourt(Integer id){
        return courtDAO.deleteCourt(id);
    }
   
}
