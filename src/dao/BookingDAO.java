/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Booking;
import config.KoneksiDatabase;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk Booking (Transaksi Booking)
 * 
 * @author gungwira
 */
public class BookingDAO {
    
    /**
     * Insert booking baru
     */
    public Booking insertBooking(Booking booking) {
        String sql = "INSERT INTO bookings (court_id, user_id, time_slot_id, booking_date, price, status, created_at, updated_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, booking.getCourt_id());
            ps.setInt(2, booking.getUser_id());
            ps.setInt(3, booking.getTime_slot_id());
            ps.setTimestamp(4, Timestamp.valueOf(booking.getBooking_date()));
            ps.setInt(5, booking.getPrice());
            ps.setString(6, booking.getStatus());
            ps.setTimestamp(7, Timestamp.valueOf(booking.getCreatedAt()));
            ps.setTimestamp(8, Timestamp.valueOf(booking.getUpdatedAt()));
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    booking.setId(generatedKeys.getInt(1));
                    generatedKeys.close();
                }
            }
            
            ps.close();
            return booking;
            
        } catch (SQLException e) {
            System.err.println("Error insert booking: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Get booking by ID
     */
    public Booking getBookingById(int id) {
        Booking booking = null;
        String sql = "SELECT * FROM bookings WHERE id = ?";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                booking = mapResultSetToBooking(rs);
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get booking by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return booking;
    }
    
    /**
     * Get bookings by court dan date
     * Untuk cek slot yang sudah dibooking
     */
    public List<Booking> getBookingsByCourtAndDate(int courtId, java.time.LocalDate date) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings " +
                     "WHERE court_id = ? AND DATE(booking_date) = ? AND status != 'cancelled' " +
                     "ORDER BY time_slot_id";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courtId);
            ps.setDate(2, java.sql.Date.valueOf(date));
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
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
     * Get all bookings hari ini
     * Untuk dashboard/monitoring
     */
    public List<Booking> getTodayBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings " +
                     "WHERE DATE(booking_date) = CURDATE() AND status != 'cancelled' " +
                     "ORDER BY court_id, time_slot_id";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get today bookings: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    /**
     * Get bookings by date range
     * Untuk laporan/revenue
     */
    public List<Booking> getBookingsByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings " +
                     "WHERE DATE(booking_date) BETWEEN ? AND ? AND status != 'cancelled' " +
                     "ORDER BY booking_date, court_id, time_slot_id";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(startDate));
            ps.setDate(2, java.sql.Date.valueOf(endDate));
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get bookings by date range: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    /**
     * Check if time slot is available
     */
    public boolean isTimeSlotAvailable(int courtId, java.time.LocalDate date, int timeSlotId) {
        String sql = "SELECT COUNT(*) FROM bookings " +
                     "WHERE court_id = ? AND DATE(booking_date) = ? AND time_slot_id = ? AND status != 'cancelled'";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courtId);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setInt(3, timeSlotId);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                rs.close();
                ps.close();
                return count == 0; // Available jika count = 0
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error check time slot availability: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Map ResultSet ke Booking object
     */
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking(rs.getInt("id"), rs.getInt("court_id"), rs.getInt("user_id"), rs.getInt("time_slot_id"), rs.getTimestamp("booking_date"), rs.getInt("price"), rs.getString("status"));
        booking.setId(rs.getInt("id"));
        booking.setCourt_id(rs.getInt("court_id"));
        booking.setUser_id(rs.getInt("user_id"));
        booking.setTime_slot_id(rs.getInt("time_slot_id"));
        
        Timestamp bookingTimestamp = rs.getTimestamp("booking_date");
        if (bookingTimestamp != null) {
            booking.setBooking_date(bookingTimestamp.toLocalDateTime());
        }
        
        booking.setPrice(rs.getInt("price"));
        booking.setStatus(rs.getString("status"));
        
        Timestamp createdTimestamp = rs.getTimestamp("created_at");
        Timestamp updatedTimestamp = rs.getTimestamp("updated_at");
        
        if (createdTimestamp != null) {
            booking.setCreatedAt(createdTimestamp.toLocalDateTime());
        }
        if (updatedTimestamp != null) {
            booking.setUpdatedAt(updatedTimestamp.toLocalDateTime());
        }
        
        return booking;
    }
    
    // ========== INNER CLASSES ==========
    
    /**
     * Class untuk daily revenue summary
     */
    public static class DailyRevenue {
        private java.time.LocalDate date;
        private int totalBookings;
        private int revenue;
        
        public DailyRevenue(java.time.LocalDate date, int totalBookings, int revenue) {
            this.date = date;
            this.totalBookings = totalBookings;
            this.revenue = revenue;
        }
        
        public java.time.LocalDate getDate() {
            return date;
        }
        
        public int getTotalBookings() {
            return totalBookings;
        }
        
        public int getRevenue() {
            return revenue;
        }
    }
}
