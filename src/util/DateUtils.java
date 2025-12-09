/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Utility untuk format tanggal
 * @author gungwira
 */
public class DateUtils {
    
    private static final Locale INDONESIAN = new Locale("id", "ID");

    public static String formatIndonesian(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", INDONESIAN);
        return date.format(formatter);
    }

    public static LocalDate today() {
        return LocalDate.now();
    }
   
    public static String formatShort(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}
