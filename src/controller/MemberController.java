/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.MemberDAO;
import java.util.List;
import model.Member;

/**
 *
 * @author gungwira
 */
public class MemberController {

    private final MemberDAO memberDAO;

    // method constructor untuk member controller
    public MemberController() {
        this.memberDAO = new MemberDAO();
    }

    // method untuk handle penambahan point pada member yang terdaftar sekaligus membuat member baru jika nomor telepon tidak terdaftar pada sistem
    public Integer handleMemberPoint(
            String name,
            String phone,
            int pointEarned
    ) {
        if (phone == null || phone.isEmpty()) {
            return -1;
        }

        return memberDAO.updateMember(name, phone, pointEarned);
    }

    // method untuk mengambil seluruh data member aktif saat ini
    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }
}
