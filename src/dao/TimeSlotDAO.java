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
 * 
 * @author gungwira
 */
public class TimeSlotDAO {
    
    // method untuk mengambil semua data timeslot yang ada
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
    
    // method untuk mengambil data timeslot yang ada berdasarkan id spesifik
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
    
    // method untuk membuat timeslot baru
    public boolean createTimeSlot(String startTime, String endTime) {
        String sql = "INSERT INTO time_slots (start_time, end_time) VALUES (?, ?)";

        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, startTime);
            ps.setString(2, endTime);

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error create time slot: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    // method untuk mengedit data timeslot berdasarkan id spesifik
    public boolean editTimeSlot(Integer id, String startTime, String endTime) {
        String sql = "UPDATE time_slots SET start_time = ?, end_time = ? WHERE id = ?";
        System.out.println("Time Start" + startTime + " on id : " + id);

        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, startTime);
            ps.setString(2, endTime);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error edit time slot: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    // method untuk menghapus data timeslot
    public boolean deleteTimeSlot(Integer id) {
        String sql = "DELETE FROM time_slots WHERE id = ?";

        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error delete time slot: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}