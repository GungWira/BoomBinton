/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.BookingDAO;
import java.util.List;
import model.Booking;

/**
 *
 * @author gungwira
 */
public class BookingController {
    final private BookingDAO bookingDAO;
    
    // method constructor untuk booking controller
    public BookingController(){
        this.bookingDAO = new BookingDAO();
    }
    
    // method untuk menambahkan booking baru
    public BookingRes addBooking(Booking booking){
        boolean res = bookingDAO.createBooking(booking);

        if(res){
            return new BookingRes(true, "Booking berhasil ditambahkan", booking);
        }

        return new BookingRes(false, "Gagal menambahkan booking", null);
    }
    
    // method untuk mengambil data booking hari ini
    public List<Booking> getTodayBookings() {
        return bookingDAO.getTodayBookings();
    }

    // method untuk mengambil data booking bulan ini
    public List<Booking> getMonthlyBookings() {
        return bookingDAO.getMonthlyBookings();
    }

    // method untuk mengambil data booking tahun ini
    public List<Booking> getYearlyBookings() {
        return bookingDAO.getYearlyBookings();
    }

    // static class untuk return type booking
    public static class BookingRes {

        final private boolean success;
        final private String message;
        final private Booking booking;

        public BookingRes(boolean success, String message, Booking booking) {
            this.success = success;
            this.message = message;
            this.booking = booking;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public Booking getBooking() {
            return booking;
        }
    }
}
