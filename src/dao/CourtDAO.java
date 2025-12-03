/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Court;
import config.KoneksiDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author gungwira
 */
public class CourtDAO {
    
    /**
     * Get semua lapangan
     */
    public List<Court> getAllCourts() {
        List<Court> courts = new ArrayList<>();
        String sql = "SELECT * FROM courts ORDER BY name";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Court court = new Court(rs.getInt("id"), rs.getString("name"), rs.getString("status"), rs.getInt("price_per_hour"));
               
                courts.add(court);
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get all courts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return courts;
    }
    
    /**
     * Get lapangan by ID
     */
    public Court getCourtById(int id) {
        Court court = null;
        String sql = "SELECT * FROM courts WHERE id = ?";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                court = new Court(rs.getInt("id"), rs.getString("name"), rs.getString("status"), rs.getInt("price_per_hour"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get court by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return court;
    }
    
    /**
     * Get lapangan dengan info availability (jumlah slot tersedia)
     * Untuk tampilan dashboard
     */
    public List<CourtAvailability> getCourtsWithAvailability(java.time.LocalDate date) {
        List<CourtAvailability> results = new ArrayList<>();
        
        String sql = "SELECT " +
                     "    c.id, " +
                     "    c.name, " +
                     "    c.status, " +
                     "    c.price_per_hour, " +
                     "    COUNT(DISTINCT ts.id) as total_slots, " +
                     "    COUNT(DISTINCT CASE WHEN b.id IS NOT NULL AND b.status != 'cancelled' THEN b.time_slot_id END) as booked_slots " +
                     "FROM courts c " +
                     "CROSS JOIN time_slots ts " +
                     "LEFT JOIN bookings b ON b.court_id = c.id " +
                     "    AND DATE(b.booking_date) = ? " +
                     "    AND b.time_slot_id = ts.id " +
                     "    AND b.status != 'cancelled' " +
                     "WHERE c.status = 'available' " +
                     "GROUP BY c.id, c.name, c.status, c.price_per_hour " +
                     "ORDER BY c.name";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(date));
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Court court = new Court(rs.getInt("id"), rs.getString("name"), rs.getString("status"), rs.getInt("price_per_hour"));
                
                int totalSlots = rs.getInt("total_slots");
                int bookedSlots = rs.getInt("booked_slots");
                int availableSlots = totalSlots - bookedSlots;
                
                CourtAvailability availability = new CourtAvailability(
                    court, totalSlots, bookedSlots, availableSlots
                );
                
                results.add(availability);
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get courts with availability: " + e.getMessage());
            e.printStackTrace();
        }
        
        return results;
    }
    
    public static class CourtAvailability {
        private Court court;
        private int totalSlots;
        private int bookedSlots;
        private int availableSlots;
        
        public CourtAvailability(Court court, int totalSlots, int bookedSlots, int availableSlots) {
            this.court = court;
            this.totalSlots = totalSlots;
            this.bookedSlots = bookedSlots;
            this.availableSlots = availableSlots;
        }
        
        public Court getCourt() {
            return court;
        }
        
        public int getTotalSlots() {
            return totalSlots;
        }
        
        public int getBookedSlots() {
            return bookedSlots;
        }
        
        public int getAvailableSlots() {
            return availableSlots;
        }
        
        public String getAvailabilityText() {
            return availableSlots + "/" + totalSlots + " Jadwal Tersedia";
        }
    }
}
