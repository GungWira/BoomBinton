/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import config.KoneksiDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.time.LocalDateTime;

/**
 *
 * @author gungwira
 */
public class UserDAO {

    // method untuk proses login ke sistem dengan pengecekan data username dan password yang ada di database
    public User authenticate(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            Connection c = KoneksiDatabase.getKoneksi();
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, username);
            p.setString(2, password);

            ResultSet res = p.executeQuery();

            if (res.next()) {
                // User ditemukan, buat object User
                user = new User(res.getInt("id"), res.getString("username"), res.getString("password"));
            }

            res.close();
            p.close();
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan : " + e);
        }
        return user;
    }
    
    // method untuk menambahkan data anggota baru
    public User register(String username, String password){
        User user = null;
        String sql = "INSERT INTO users (username, password, created_at, updated_at) VALUES (?, ?, ?, ?)";
        
        try {
            Connection c = KoneksiDatabase.getKoneksi();
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, username);
            p.setString(2, password);
            p.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            p.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            
            int rowsAffected = p.executeUpdate();
            
            if(rowsAffected > 0){
                user = this.authenticate(username, password);
      
                p.close();
                return user;
            }
            
            p.close();
            return user;
            
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan : " + e);
            return user;
        }
    }
    
    // method untuk memriksa apakah username ada pada database atau tidak
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try {
            Connection c = KoneksiDatabase.getKoneksi();
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, username);
            
            ResultSet res = p.executeQuery();
            
            if (res.next()) {
                int count = res.getInt(1);
                res.close();
                p.close();
                return count > 0;
            }
            
            res.close();
            p.close();
            
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan : " + e);
        }
        
        return false;
    }
}
