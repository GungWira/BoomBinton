/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Court;
import config.KoneksiDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author gungwira
 */
public class CourtDAO {
    
    /**
     * Get semua lapangan
     */
    public List<Court> getAllCourts() {
        List<Court> courts = new ArrayList<>();
        String sql = "SELECT * FROM courts ORDER BY name";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Court court = new Court(rs.getInt("id"), rs.getString("name"), rs.getString("status"), rs.getInt("price_per_hour"));
               
                courts.add(court);
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get all courts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return courts;
    }
    
    /**
     * Get lapangan by ID
     */
    public Court getCourtById(int id) {
        Court court = null;
        String sql = "SELECT * FROM courts WHERE id = ?";
        
        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                court = new Court(rs.getInt("id"), rs.getString("name"), rs.getString("status"), rs.getInt("price_per_hour"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Error get court by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return court;
    }
    
}
