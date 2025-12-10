/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.dashboard;

import controller.CourtController;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.Court;
import util.DateUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import model.TimeSlot;
import java.util.List;
import javax.swing.BorderFactory;

/**
 *
 * @author gungwira
 */
public class CourtDetailPanel extends javax.swing.JPanel {
    private DashboardFrame dashboardFrame;
    private Court selectedCourt;
    private CourtController controller;
    private LocalDate selectedDate;
    private JPanel timeSlotPanel;
    private Map<Integer, JButton> timeSlotButtons;
    private List<TimeSlot> selectedSlots = new ArrayList<>();

    /**
     * Creates new form CourtDetailPanel
     */
    public CourtDetailPanel(DashboardFrame dashboardFrame) {
        this.dashboardFrame = dashboardFrame;
        this.controller = new CourtController();
        this.selectedDate = DateUtils.today();
        this.timeSlotButtons = new HashMap<>();

        initComponents();

        setSize(1280, 754);
    }
    
    /**
     * Set court dan load timeslots
     */
    public void setCourt(Court court) {
        
        this.selectedCourt = court;
        
        labelOrders.setText("");
        timeSlotButtons.clear();
        selectedSlots.clear();
        
        courtDetailTitle.setText("Lapangan " + selectedCourt.getName());
        jLabel5.setText(DateUtils.formatIndonesian(selectedDate));
        confirmButton.setOpaque(true);
        
        setupTimeSlotPanel();

        
    }
    
    private void setupTimeSlotPanel() {
        // fetch data timeslot
        List<TimeSlot> timeslots = controller.getAllTimeSlots();

        // fetch data timeslot yang sudah dibooking
        List<Integer> bookedTimeslots = controller.getBookedTimeSlotIds(selectedCourt.getId(), LocalDate.now());

        for (int i = 0; i < timeSlotContainer.getComponentCount(); i++) {
            Component comp = timeSlotContainer.getComponent(i);
            if (comp instanceof JButton btn) {
                setupButtonForTimeSlot(btn, timeslots.get(i), bookedTimeslots);
            }
        }
        
        totalAvailableSlot.setText("Jam booking tersedia : " + (15 - bookedTimeslots.size()) + "/15");
        
    }
    
    private void toggleButtonSelection(JButton btn, TimeSlot slot) {
        Color selectedColor = new Color(79, 138, 107);
        Color defaultBg = Color.WHITE;
        Color defaultFg = Color.BLACK;

        boolean isSelected = btn.getBackground().equals(selectedColor);

        if (isSelected) {
            // Normal
            btn.setBackground(defaultBg);
            btn.setForeground(defaultFg);
            selectedSlots.remove(slot);
        } else {
            // Pilih
            btn.setBackground(selectedColor);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            selectedSlots.add(slot);

        }
        
        updateOrderDetails();
        
    }


    private void setupButtonForTimeSlot(
            JButton btn,
            TimeSlot slot,
            List<Integer> bookedTimeslots
    ) {
        int slotId = slot.getId();

        // Set teks
        btn.setText(slot.getStart_time() + " - " + slot.getEnd_time());
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);

        // Jika sudah dibooking user lain → disable
        if (bookedTimeslots.contains(slotId)) {
            btn.setEnabled(false);
            btn.setBackground(new Color(200, 200, 200)); // abu-abu
            btn.setForeground(Color.DARK_GRAY);
            return;
        }

        // Slot available
        btn.setEnabled(true);
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));


        // Tambahkan klik event
        btn.addActionListener(e -> toggleButtonSelection(btn, slot));
    }
    
    private void updateOrderDetails() {
        StringBuilder sb = new StringBuilder();
        int total = 0;

        for (TimeSlot slot : selectedSlots) {
            sb.append(slot.getStart_time())
              .append(" - ")
              .append(slot.getEnd_time())
              .append("\n\n");

            total += selectedCourt.getPrice_per_hour();
        }

        labelOrders.setText("<html>" + sb.toString().replace("\n", "<br>") + "</html>");
        labelTotal.setText("Total: Rp " + total);
    }

   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sidebar = new javax.swing.JPanel();
        sidebarLogo = new javax.swing.JLabel();
        sidebarUsersButton = new javax.swing.JButton();
        SidebarLogoSeperator = new javax.swing.JPanel();
        sidebarDashboardButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        courtDetailTitle = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        mainContainer = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        totalAvailableSlot = new javax.swing.JLabel();
        timeSlotContainer = new javax.swing.JPanel();
        buttonId1 = new javax.swing.JButton();
        buttonId2 = new javax.swing.JButton();
        buttonId3 = new javax.swing.JButton();
        buttonId4 = new javax.swing.JButton();
        buttonId5 = new javax.swing.JButton();
        buttonId6 = new javax.swing.JButton();
        buttonId7 = new javax.swing.JButton();
        buttonId8 = new javax.swing.JButton();
        buttonId9 = new javax.swing.JButton();
        buttonId10 = new javax.swing.JButton();
        buttonId11 = new javax.swing.JButton();
        buttonId12 = new javax.swing.JButton();
        buttonId13 = new javax.swing.JButton();
        buttonId14 = new javax.swing.JButton();
        buttonId15 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        labelTotal = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        labelOrders = new javax.swing.JLabel();
        confirmButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(234, 234, 234));
        setSize(new java.awt.Dimension(1280, 720));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidebar.setBackground(new java.awt.Color(14, 60, 61));
        sidebar.setForeground(new java.awt.Color(14, 60, 61));
        sidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidebarLogo.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        sidebarLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/boombinton_logo.png"))); // NOI18N
        sidebarLogo.setText("BoomBinton");
        sidebar.add(sidebarLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 180, 40));

        sidebarUsersButton.setBackground(new java.awt.Color(14, 60, 61));
        sidebarUsersButton.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        sidebarUsersButton.setForeground(new java.awt.Color(255, 255, 255));
        sidebarUsersButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/users-icon.png"))); // NOI18N
        sidebarUsersButton.setText(" Users");
        sidebarUsersButton.setBorder(null);
        sidebarUsersButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sidebarUsersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidebarUsersButtonActionPerformed(evt);
            }
        });
        sidebar.add(sidebarUsersButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 180, 40));

        SidebarLogoSeperator.setBackground(new java.awt.Color(20, 85, 87));

        javax.swing.GroupLayout SidebarLogoSeperatorLayout = new javax.swing.GroupLayout(SidebarLogoSeperator);
        SidebarLogoSeperator.setLayout(SidebarLogoSeperatorLayout);
        SidebarLogoSeperatorLayout.setHorizontalGroup(
            SidebarLogoSeperatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        SidebarLogoSeperatorLayout.setVerticalGroup(
            SidebarLogoSeperatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        sidebar.add(SidebarLogoSeperator, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 180, 1));

        sidebarDashboardButton.setBackground(new java.awt.Color(14, 60, 61));
        sidebarDashboardButton.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        sidebarDashboardButton.setForeground(new java.awt.Color(255, 255, 255));
        sidebarDashboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/dashboard-icon.png"))); // NOI18N
        sidebarDashboardButton.setText(" Dashboard");
        sidebarDashboardButton.setBorder(null);
        sidebarDashboardButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sidebarDashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidebarDashboardButtonActionPerformed(evt);
            }
        });
        sidebar.add(sidebarDashboardButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 180, 40));

        jLabel6.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Admin Dashboard");
        sidebar.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        add(sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 720));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        courtDetailTitle.setFont(new java.awt.Font("Heiti SC", 0, 24)); // NOI18N
        courtDetailTitle.setText("Lapangan A");
        header.add(courtDetailTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel8.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Pilih jam sewa yang ingin dibooking");
        header.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 65, -1, -1));

        jButton1.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        jButton1.setText("Konfirmasi");
        header.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 610, 190, 50));

        add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 1060, 110));

        mainContainer.setBackground(new java.awt.Color(255, 255, 255));
        mainContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Heiti SC", 0, 20)); // NOI18N
        jLabel5.setText("Rabu, 3 Desember 2025");
        mainContainer.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        totalAvailableSlot.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        totalAvailableSlot.setForeground(new java.awt.Color(102, 102, 102));
        totalAvailableSlot.setText("Jam tersedia : 8/12");
        mainContainer.add(totalAvailableSlot, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        timeSlotContainer.setBackground(new java.awt.Color(255, 255, 255));
        timeSlotContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonId1.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId1.setText("08.00 - 09.00");
        buttonId1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId1ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 60));

        buttonId2.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId2.setText("08.00 - 09.00");
        buttonId2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId2ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId2, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 0, 210, 60));

        buttonId3.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId3.setText("08.00 - 09.00");
        buttonId3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId3ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 210, 60));

        buttonId4.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId4.setText("08.00 - 09.00");
        buttonId4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId4ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 210, 60));

        buttonId5.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId5.setText("08.00 - 09.00");
        buttonId5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId5ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId5, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 70, 210, 60));

        buttonId6.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId6.setText("08.00 - 09.00");
        buttonId6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId6ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 210, 60));

        buttonId7.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId7.setText("08.00 - 09.00");
        buttonId7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId7ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 210, 60));

        buttonId8.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId8.setText("08.00 - 09.00");
        buttonId8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId8ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId8, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 140, 210, 60));

        buttonId9.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId9.setText("08.00 - 09.00");
        buttonId9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId9ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId9, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 210, 60));

        buttonId10.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId10.setText("08.00 - 09.00");
        buttonId10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId10ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 210, 60));

        buttonId11.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId11.setText("08.00 - 09.00");
        buttonId11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId11ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId11, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 210, 210, 60));

        buttonId12.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId12.setText("08.00 - 09.00");
        buttonId12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId12ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 210, 210, 60));

        buttonId13.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId13.setText("08.00 - 09.00");
        buttonId13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId13ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 210, 60));

        buttonId14.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId14.setText("08.00 - 09.00");
        buttonId14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId14ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId14, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 280, 210, 60));

        buttonId15.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        buttonId15.setText("08.00 - 09.00");
        buttonId15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonId15ActionPerformed(evt);
            }
        });
        timeSlotContainer.add(buttonId15, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, 210, 60));

        mainContainer.add(timeSlotContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 660, 340));

        add(mainContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 730, 560));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTotal.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        labelTotal.setForeground(new java.awt.Color(102, 102, 102));
        labelTotal.setText("Total : Rp 0.000");
        jPanel2.add(labelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 190, -1));

        jLabel9.setFont(new java.awt.Font("Heiti SC", 0, 20)); // NOI18N
        jLabel9.setText("Ringkasan Pesanan");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel11.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Detail pesanan");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        jScrollPane1.setBackground(new java.awt.Color(244, 244, 244));
        jScrollPane1.setBorder(null);

        jPanel3.setBackground(new java.awt.Color(249, 249, 249));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelOrders.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        labelOrders.setForeground(new java.awt.Color(102, 102, 102));
        labelOrders.setText("Belum ada data..");
        jPanel3.add(labelOrders, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 140, -1));

        jScrollPane1.setViewportView(jPanel3);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 190, 330));

        confirmButton.setBackground(new java.awt.Color(0, 51, 51));
        confirmButton.setFont(new java.awt.Font("Heiti SC", 1, 16)); // NOI18N
        confirmButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmButton.setText("Konfirmasi");
        confirmButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });
        jPanel2.add(confirmButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 190, 50));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 130, 250, 560));
    }// </editor-fold>//GEN-END:initComponents

    private void sidebarUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarUsersButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sidebarUsersButtonActionPerformed

    private void sidebarDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarDashboardButtonActionPerformed
        dashboardFrame.showCourtPanel();
    }//GEN-LAST:event_sidebarDashboardButtonActionPerformed

    private void buttonId3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId3ActionPerformed

    private void buttonId2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId2ActionPerformed

    private void buttonId1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId1ActionPerformed

    private void buttonId5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId5ActionPerformed

    private void buttonId6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId6ActionPerformed

    private void buttonId4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId4ActionPerformed

    private void buttonId9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId9ActionPerformed

    private void buttonId8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId8ActionPerformed

    private void buttonId7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId7ActionPerformed

    private void buttonId12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId12ActionPerformed

    private void buttonId11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId11ActionPerformed

    private void buttonId10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId10ActionPerformed

    private void buttonId13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId13ActionPerformed

    private void buttonId14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId14ActionPerformed

    private void buttonId15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonId15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonId15ActionPerformed

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        dashboardFrame.getPaymentPanel().setupData(selectedCourt, selectedSlots, selectedDate);
        dashboardFrame.showPaymentPanel();
    }//GEN-LAST:event_confirmButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SidebarLogoSeperator;
    private javax.swing.JButton buttonId1;
    private javax.swing.JButton buttonId10;
    private javax.swing.JButton buttonId11;
    private javax.swing.JButton buttonId12;
    private javax.swing.JButton buttonId13;
    private javax.swing.JButton buttonId14;
    private javax.swing.JButton buttonId15;
    private javax.swing.JButton buttonId2;
    private javax.swing.JButton buttonId3;
    private javax.swing.JButton buttonId4;
    private javax.swing.JButton buttonId5;
    private javax.swing.JButton buttonId6;
    private javax.swing.JButton buttonId7;
    private javax.swing.JButton buttonId8;
    private javax.swing.JButton buttonId9;
    private javax.swing.JButton confirmButton;
    private javax.swing.JLabel courtDetailTitle;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelOrders;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JPanel mainContainer;
    private javax.swing.JPanel sidebar;
    private javax.swing.JButton sidebarDashboardButton;
    private javax.swing.JLabel sidebarLogo;
    private javax.swing.JButton sidebarUsersButton;
    private javax.swing.JPanel timeSlotContainer;
    private javax.swing.JLabel totalAvailableSlot;
    // End of variables declaration//GEN-END:variables
}
