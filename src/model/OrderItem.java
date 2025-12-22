/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gungwira
 */

// interface class untuk order item yang akan diterapkan oleh abstract class yang kemudian baru digunakan oleh childrennya seperti racket dan kok
public interface OrderItem {
    String getName();
    int getPrice();
    int getQuantity();
    int getSubtotal();

}
