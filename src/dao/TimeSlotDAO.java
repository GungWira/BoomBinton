/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.TimeSlot;
import config.KoneksiDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk TimeSlot
 * @author gungwira
 */
public class TimeSlotDAO {
    
    public List<TimeSlot> getAllTimeSlots() {
        List<TimeSlot> timeSlots = new ArrayList<>();
        String sql = "SELECT * FROM time_slots";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                TimeSlot timeSlot = new TimeSlot(
                    rs.getInt("id"),
                    rs.getString("start_time"),
                    rs.getString("end_time")
                );
                timeSlots.add(timeSlot);
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get all time slots: " + e.getMessage());
            e.printStackTrace();
        }
        
        return timeSlots;
    }
    
    /**
     * Get time slot by ID
     */
    public TimeSlot getTimeSlotById(int id) {
        TimeSlot timeSlot = null;
        String sql = "SELECT * FROM time_slots WHERE id = ?";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                timeSlot = new TimeSlot(
                    rs.getInt("id"),
                    rs.getString("start_time"),
                    rs.getString("end_time")
                );
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get time slot by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return timeSlot;
    }
}