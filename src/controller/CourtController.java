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
    private CourtDAO courtDAO;
    private TimeSlotDAO timeSlotDAO;
    private BookingDAO bookingDAO;
    private List<TimeSlot> selectedTimeSlots;
    
    public CourtController(){
        this.courtDAO = new CourtDAO();
        this.timeSlotDAO = new TimeSlotDAO();
        this.bookingDAO = new BookingDAO();
        this.selectedTimeSlots = new ArrayList<>();
    }
    
    public List<Court> getAllCourts() {
        try {
            return courtDAO.getAllCourts();
        } catch (Exception e) {
            System.err.println("Error di CourtController.getAllCourts: " + e.getMessage());
            return null;
        }
    }
    
    public Court getCourtById(Integer id){
        try {
            return courtDAO.getCourtById(id);
        } catch (Exception e) {
            System.err.println("Error di CourtController.getCourtById: " + e.getMessage());
            return null;
        }
    }
    
    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotDAO.getAllTimeSlots();
    }

    public List<Integer> getBookedTimeSlotIds(int courtId, LocalDate date) {
        return bookingDAO.getBookedTimeSlotIds(courtId, date);
    }
    
    public boolean isTimeSlotBooked(int courtId, int timeSlotId, LocalDate date) {
        return bookingDAO.isTimeSlotBooked(courtId, timeSlotId, date);
    }
    
    public void toggleTimeSlotSelection(TimeSlot timeSlot) {
        if (isTimeSlotSelected(timeSlot)) {
            selectedTimeSlots.remove(timeSlot);
        } else {
            selectedTimeSlots.add(timeSlot);
        }
    }
    
    public boolean isTimeSlotSelected(TimeSlot timeSlot) {
        return selectedTimeSlots.stream()
            .anyMatch(ts -> ts.getId().equals(timeSlot.getId()));
    }
    
    public List<TimeSlot> getSelectedTimeSlots() {
        return new ArrayList<>(selectedTimeSlots);
    }
    
    public void clearSelection() {
        selectedTimeSlots.clear();
    }
    
    public int getAvailableTimeSlotsCount(int courtId, LocalDate date) {
        List<TimeSlot> allTimeSlots = getAllTimeSlots();
        List<Integer> bookedIds = getBookedTimeSlotIds(courtId, date);
        
        return (int) allTimeSlots.stream()
            .filter(ts -> !bookedIds.contains(ts.getId()))
            .count();
    }

    public int calculateTotalPrice(int pricePerHour) {
        return selectedTimeSlots.size() * pricePerHour;
    }
   
}
