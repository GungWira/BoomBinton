/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CourtDAO;
import dao.TimeSlotDAO;
import java.time.LocalDate;
import java.util.List;
import dao.CourtDAO.CourtAvailability;
import model.Court;

/**
 *
 * @author gungwira
 */
public class CourtController {
    private CourtDAO courtDAO;
    private TimeSlotDAO timeSlotDAO;
    
    public CourtController(){
        this.courtDAO = new CourtDAO();
        this.timeSlotDAO = new TimeSlotDAO();
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
}
