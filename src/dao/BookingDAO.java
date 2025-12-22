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
 *
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

    // ===============================
    // BOOKING HARI INI
    // ===============================
    public List<Booking> getTodayBookings() {
        String sql = """
            SELECT 
                b.*, 
                c.name AS court_name,
                t.start_time,
                t.end_time
            FROM bookings b
            JOIN courts c ON b.court_id = c.id
            JOIN time_slots t ON b.time_slot_id = t.id
            WHERE DATE(b.booking_date) = CURDATE()
            ORDER BY b.booking_date
        """;

        return getBookings(sql);
    }

    // ===============================
    // BOOKING 1 BULAN
    // ===============================
    public List<Booking> getMonthlyBookings() {
        String sql = """
            SELECT 
                b.*, 
                c.name AS court_name,
                t.start_time,
                t.end_time
            FROM bookings b
            JOIN courts c ON b.court_id = c.id
            JOIN time_slots t ON b.time_slot_id = t.id
            WHERE b.booking_date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
            ORDER BY b.booking_date DESC
        """;

        return getBookings(sql);
    }

    // ===============================
    // BOOKING 1 TAHUN
    // ===============================
    public List<Booking> getYearlyBookings() {
        String sql = """
            SELECT 
                b.*, 
                c.name AS court_name,
                t.start_time,
                t.end_time
            FROM bookings b
            JOIN courts c ON b.court_id = c.id
            JOIN time_slots t ON b.time_slot_id = t.id
            WHERE b.booking_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)
            ORDER BY b.booking_date DESC
        """;

        return getBookings(sql);
    }

    // ===============================
    // HELPER (BIAR TIDAK DUPLIKASI)
    // ===============================
    private List<Booking> getBookings(String sql) {
        List<Booking> bookings = new ArrayList<>();

        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setCourt_id(rs.getInt("court_id"));
                booking.setName(rs.getString("name"));
                booking.setPhone(rs.getString("phone"));
                booking.setTime_slot_id(rs.getInt("time_slot_id"));
                booking.setBooking_date(rs.getTimestamp("booking_date").toLocalDateTime());
                booking.setPrice(rs.getInt("price"));
                booking.setStatus(rs.getString("status"));
                booking.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                booking.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                // data JOIN
                booking.setCourtName(rs.getString("court_name"));
                booking.setStartTime(rs.getString("start_time"));
                booking.setEndTime(rs.getString("end_time"));

                bookings.add(booking);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("Error get bookings: " + e.getMessage());
            e.printStackTrace();
        }

        return bookings;
    }
}
