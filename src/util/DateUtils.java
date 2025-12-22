/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 
 * @author gungwira
 */
public class DateUtils {
    // variable static untuk menyimpan local time yang akan digunakan
    private static final Locale INDONESIAN = new Locale("id", "ID");
    
    // helper method untuk format tanggal menjadi string dengan format tanggal Indonesia lengkap (digunakan di receiptPdf)
    public static String formatIndonesian(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", INDONESIAN);
        return date.format(formatter);
    }

    // helper method untuk mencari data hari ini
    public static LocalDate today() {
        return LocalDate.now();
    }
   
    // helper method untuk untuk format data tanggal indonesia dengan format seperti 17/10/2025
    public static String formatShort(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}
