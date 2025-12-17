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
    private BookingDAO bookingDAO;
    
    public BookingController(){
        this.bookingDAO = new BookingDAO();
    }
    
    public BookingRes addBooking(Booking booking){
        boolean res = bookingDAO.createBooking(booking);

        if(res){
            return new BookingRes(true, "Booking berhasil ditambahkan", booking);
        }

        return new BookingRes(false, "Gagal menambahkan booking", null);
    }
    
    public List<Booking> getTodayBookings() {
        return bookingDAO.getTodayBookings();
    }

    public List<Booking> getMonthlyBookings() {
        return bookingDAO.getMonthlyBookings();
    }

    public List<Booking> getYearlyBookings() {
        return bookingDAO.getYearlyBookings();
    }

    
    public static class BookingRes {

        private boolean success;
        private String message;
        private Booking booking;

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
