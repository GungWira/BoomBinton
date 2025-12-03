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
 * DAO untuk TimeSlot (Slot Waktu)
 * 
 * @author gungwira
 */
public class TimeSlotDAO {
    
    /**
     * Get semua time slots
     */
    public List<TimeSlot> getAllTimeSlots() {
        List<TimeSlot> timeSlots = new ArrayList<>();
        String sql = "SELECT * FROM time_slots ORDER BY start_time";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                TimeSlot slot = new TimeSlot(rs.getInt("id"), rs.getString("start_time"), rs.getString("end_time"));
                
                timeSlots.add(slot);
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
        TimeSlot slot = null;
        String sql = "SELECT * FROM time_slots WHERE id = ?";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                slot = new TimeSlot(rs.getInt("id"), rs.getString("start_time"), rs.getString("end_time"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get time slot by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return slot;
    }
    
    /**
     * Get time slots dengan status booking untuk lapangan tertentu
     * Untuk tampilan detail lapangan dengan slot booking
     */
    public List<TimeSlotStatus> getTimeSlotsWithStatus(int courtId, java.time.LocalDate date) {
        List<TimeSlotStatus> results = new ArrayList<>();
        
        String sql = "SELECT " +
                     "    ts.id, " +
                     "    ts.start_time, " +
                     "    ts.end_time, " +
                     "    b.id as booking_id, " +
                     "    b.status as booking_status " +
                     "FROM time_slots ts " +
                     "LEFT JOIN bookings b ON b.time_slot_id = ts.id " +
                     "    AND b.court_id = ? " +
                     "    AND DATE(b.booking_date) = ? " +
                     "    AND b.status != 'cancelled' " +
                     "ORDER BY ts.start_time";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courtId);
            ps.setDate(2, java.sql.Date.valueOf(date));
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                TimeSlot slot = new TimeSlot(rs.getInt("id"), rs.getString("start_time"), rs.getString("end_time"));
                
                Integer bookingId = rs.getObject("booking_id", Integer.class);
                String bookingStatus = rs.getString("booking_status");
                
                boolean isBooked = (bookingId != null);
                
                TimeSlotStatus status = new TimeSlotStatus(slot, isBooked, bookingStatus);
                results.add(status);
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get time slots with status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return results;
    }
    
    /**
     * Class untuk return time slot dengan status booking
     */
    public static class TimeSlotStatus {
        private TimeSlot timeSlot;
        private boolean isBooked;
        private String bookingStatus;
        
        public TimeSlotStatus(TimeSlot timeSlot, boolean isBooked, String bookingStatus) {
            this.timeSlot = timeSlot;
            this.isBooked = isBooked;
            this.bookingStatus = bookingStatus;
        }
        
        public TimeSlot getTimeSlot() {
            return timeSlot;
        }
        
        public boolean isBooked() {
            return isBooked;
        }
        
        public boolean isAvailable() {
            return !isBooked;
        }
        
        public String getBookingStatus() {
            return bookingStatus;
        }
        
        /**
         * Get display text untuk button
         * Format: "08:00 - 09:00"
         */
        public String getDisplayText() {
            String start = timeSlot.getStart_time();
            String end = timeSlot.getEnd_time();
            
            // Format dari "08:00:00" jadi "08:00"
            if (start != null && start.length() >= 5) {
                start = start.substring(0, 5);
            }
            if (end != null && end.length() >= 5) {
                end = end.substring(0, 5);
            }
            
            return start + " - " + end;
        }
    }
}
