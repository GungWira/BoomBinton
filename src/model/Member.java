/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gungwira
 */
public class Member {
    private Integer id;
    private String name;
    private String phone;
    private Integer point;

    public Member() {
    }

    public Member(Integer id, String name, String phone, Integer point) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.point = point;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getPoint() {
        return point;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
