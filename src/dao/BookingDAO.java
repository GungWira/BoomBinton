/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Booking;
import config.KoneksiDatabase;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk Booking
 * @author gungwira
 */
public class BookingDAO {
    
    public List<Booking> getBookingsByCourtAndDate(int courtId, LocalDate date) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE court_id = ? AND DATE(booking_date) = ? AND status IN ('confirmed', 'pending')";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courtId);
            ps.setDate(2, Date.valueOf(date));
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("id"),
                    rs.getInt("court_id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getInt("time_slot_id"),
                    rs.getTimestamp("booking_date").toLocalDateTime(),
                    rs.getInt("price"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                );
                bookings.add(booking);
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get bookings by court and date: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    /**
     * Cek apakah timeslot sudah dibooking
     */
    public boolean isTimeSlotBooked(int courtId, int timeSlotId, LocalDate date) {
        String sql = "SELECT COUNT(*) as count FROM bookings WHERE court_id = ? AND time_slot_id = ? AND DATE(booking_date) = ? AND status IN ('confirmed', 'pending')";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courtId);
            ps.setInt(2, timeSlotId);
            ps.setDate(3, Date.valueOf(date));
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt("count");
                rs.close();
                ps.close();
                return count > 0;
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error check if timeslot is booked: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Create booking baru
     */
    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (court_id, name, phone, time_slot_id, booking_date, price, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, booking.getCourt_id());
            ps.setString(2, booking.getName());
            ps.setString(3, booking.getPhone());
            ps.setInt(4, booking.getTime_slot_id());
            ps.setTimestamp(5, Timestamp.valueOf(booking.getBooking_date()));
            ps.setInt(6, booking.getPrice());
            ps.setString(7, booking.getStatus());
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            
            int rowsAffected = ps.executeUpdate();
            ps.close();
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error create booking: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Get booked timeslot IDs untuk court dan tanggal tertentu
     */
    public List<Integer> getBookedTimeSlotIds(int courtId, LocalDate date) {
        List<Integer> bookedIds = new ArrayList<>();
        String sql = "SELECT time_slot_id FROM bookings WHERE court_id = ? AND DATE(booking_date) = ? AND status IN ('booked', 'pending')";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courtId);
            ps.setDate(2, Date.valueOf(date));
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                bookedIds.add(rs.getInt("time_slot_id"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get booked timeslot IDs: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookedIds;
    }
}