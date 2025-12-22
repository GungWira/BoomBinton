/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author gungwira
 */
import model.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import config.KoneksiDatabase;

public class MemberDAO {

    // method untuk update data member berupa memperbarui jumlah point maupun nama member dengan kode unik telepon serta membuat member baru jika data telepon belum tercatat
    public Integer updateMember(String name, String phone, int pointEarned) {

        String checkSql = "SELECT point FROM members WHERE phone = ?";
        String updateSql = "UPDATE members SET name = ?, point = ? WHERE phone = ?";
        String insertSql = "INSERT INTO members (name, phone, point) VALUES (?, ?, ?)";
        
        int newPoint = 0;

        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, phone);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // Member sudah ada → update
                int currentPoint = rs.getInt("point");
                newPoint = currentPoint + pointEarned;

                PreparedStatement updatePs = conn.prepareStatement(updateSql);
                updatePs.setString(1, name);
                updatePs.setInt(2, newPoint);
                updatePs.setString(3, phone);
                updatePs.executeUpdate();

                updatePs.close();
            } else {
                // Member belum ada → insert
                PreparedStatement insertPs = conn.prepareStatement(insertSql);
                insertPs.setString(1, name);
                insertPs.setString(2, phone);
                insertPs.setInt(3, pointEarned);
                insertPs.executeUpdate();
                
                newPoint = pointEarned;

                insertPs.close();
            }

            rs.close();
            checkPs.close();

            return newPoint;

        } catch (SQLException e) {
            System.err.println("Error update member: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    // method untuk mengambil semua data member
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members ORDER BY point DESC";

        try {
            Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Member member = new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("point")
                );
                members.add(member);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("Error get all members: " + e.getMessage());
            e.printStackTrace();
        }

        return members;
    }
}
