/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.time.LocalDateTime;

/**
 *
 * @author gungwira
 */
public class SessionManager {
    private static SessionManager instance;
    
    private Integer userId;
    private String username;
    private LocalDateTime loginTime;
    private boolean isLoggedIn;
    
    private SessionManager(){
        this.isLoggedIn = false;
    }
    
    public static SessionManager getInstance(){
        if (instance == null){
            instance = new SessionManager();
        }
        return instance;
    }
    
    // method untuk login dan menyimpan data login pengguna
    public void login(Integer userId, String username) {
        this.userId = userId;
        this.username = username;
        this.loginTime = LocalDateTime.now();
        this.isLoggedIn = true;
        
        System.out.println("Sesi login berhasil dibuat");
    }
    
    // method untuk logout dan menghapus data sesi login pengguna
    public void logout() {
        this.userId = null;
        this.username = null;
        this.loginTime = null;
        this.isLoggedIn = false;
        
        System.out.println("Sesi login berhasil dihapus");
    }
    
    // helper method untuk memeriksa status login
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    // helper method untuk memeriksa apakah sesi valid (maksimal 8 jam)
    public boolean isSessionValid() {
        if (!isLoggedIn) {
            return false;
        }
        
        if (loginTime != null) {
            LocalDateTime now = LocalDateTime.now();
            long hoursDiff = java.time.Duration.between(loginTime, now).toHours();
            
            if (hoursDiff > 8) {
                logout(); 
                return false;
            }
        }
        
        return true;
    }
}
