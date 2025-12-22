/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author gungwira
 */
public class KoneksiDatabase {
    private static Connection koneksi;
    
    // menthod untuk konfigurasi koneksi ke database
    public static Connection getKoneksi(){
        
        if (koneksi == null){
            try{
                String url = "jdbc:mysql://localhost:3306/boombinton";
                String user = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
            }catch (SQLException t){
                System.out.println("Error memuat Koneksi");
            }
        }
        return koneksi;
    }
}
