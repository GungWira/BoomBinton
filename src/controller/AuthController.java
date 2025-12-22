/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import model.User;
import util.SessionManager;

/**
 *
 * @author gungwira
 */
public class AuthController {

    final private UserDAO userDAO;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    // method untuk handle proses login pada sistem
    public LoginRes login(String username, String password) {
        // Validasi input kosong
        if (username == null || username.trim().isEmpty()) {
            return new LoginRes(false, "Username tidak boleh kosong", null);
        }

        if (password == null || password.trim().isEmpty()) {
            return new LoginRes(false, "Password tidak boleh kosong", null);
        }

        // Validasi placeholder
        if (username.equals("Masukkan username...")) {
            return new LoginRes(false, "Masukkan username terlebih dahulu", null);
        }

        if (password.equals("Masukkan password...")) {
            return new LoginRes(false, "Masukkan password terlebih dahulu", null);
        }

        User user = userDAO.authenticate(username, password);

        if (user != null) {
            SessionManager.getInstance().login(user.getId(), user.getUsername());
            return new LoginRes(true, "Login berhasil", user);
        } else {
            return new LoginRes(false, "Username atau password salah", null);
        }
    }

    // method untuk handle proses registrasi user baru pada aplikasi
    public RegisterRes register(String username, String password, String confirmPassword) {
        // Validasi input kosong
        if (username == null || username.trim().isEmpty()) {
            return new RegisterRes(false, "Username tidak boleh kosong");
        }
        if (password == null || password.trim().isEmpty()) {
            return new RegisterRes(false, "Password tidak boleh kosong");
        }
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return new RegisterRes(false, "Konfirmasi password tidak boleh kosong");
        }

        // Validasi placeholder
        if (username.equals("Masukkan username...")) {
            return new RegisterRes(false, "Masukkan username terlebih dahulu");
        }
        if (password.equals("Masukkan password...")) {
            return new RegisterRes(false, "Masukkan password terlebih dahulu");
        }
        if (confirmPassword.equals("Konfirmasi Password...")) {
            return new RegisterRes(false, "Masukkan konfirmasi password terlebih dahulu");
        }

        // Validasi password match
        if (!password.equals(confirmPassword)) {
            return new RegisterRes(false, "Password dan konfirmasi password tidak cocok!");
        }

        // Cek username sudah ada?
        if (userDAO.isUsernameExists(username)) {
            return new RegisterRes(false, "Username sudah digunakan, silakan pilih username lain");
        }

        // buat user baru
        User newUser = userDAO.register(username, password);

        if (newUser != null) {
            // atur session agar user bisa tetap login
            SessionManager.getInstance().login(newUser.getId(), newUser.getUsername());
            return new RegisterRes(true, "Registrasi berhasil! Silakan login");
        } else {
            return new RegisterRes(false, "Terjadi kesalahan saat registrasi, coba lagi");
        }
    }

    // method untuk logout pengguna
    public void logout() {
        SessionManager.getInstance().logout();
    }

    // static class untuk return type pada sistem login
    public static class LoginRes {

        final private boolean success;
        final private String message;
        final private User user;

        public LoginRes(boolean success, String message, User user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public User getUser() {
            return user;
        }
    }

    // static class untuk return type pada sistem registrasi pengguna baru
    public static class RegisterRes {

        final private boolean success;
        final private String message;

        public RegisterRes(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

}
