/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.dashboard;

import controller.TimeslotController;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import model.Court;
import model.TimeSlot;

/**
 *
 * @author gungwira
 */
public class TimeSlotPanel extends javax.swing.JPanel {

    final private DashboardFrame dashboardFrame;
    final private TimeslotController timeSlotController;
    private TimeSlot editedTimeSlot;

    /**
     * Creates new form TimeSlotPanel
     */
    public TimeSlotPanel(DashboardFrame dashboardFrame) {
        this.dashboardFrame = dashboardFrame;
        this.timeSlotController = new TimeslotController();
        this.editedTimeSlot = null;

        initComponents();

        setSize(1280, 754);
    }

    public void setupTimeslot() {
        mainContainer.removeAll();
        List<TimeSlot> timeSlots = timeSlotController.getAllTimeSlots();

        for (TimeSlot timeSlot : timeSlots) {
            JPanel card = createTimeSlotCard(timeSlot);
            mainContainer.add(card);
        }

        int cardHeight = 80;
        int spacing = 10;
        int totalHeight = (timeSlots.size() * cardHeight) + ((timeSlots.size() - 1) * spacing);

        mainContainer.setPreferredSize(new Dimension(560, totalHeight));
        mainContainer.revalidate();
        mainContainer.repaint();

        buttonCreate.setContentAreaFilled(false);
        buttonCreate.setOpaque(true);

        setNumericOnly(inputStartTime);
        setNumericOnly(inputEndTime);

        buttonBackToAdd.setVisible(false);
        editedTimeSlot = null;

    }

    public JPanel createTimeSlotCard(
            TimeSlot timeslot
    ) {
        JPanel coverCard = new JPanel();
        coverCard.setBackground(new java.awt.Color(249, 249, 249));
        coverCard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        coverCard.setPreferredSize(new java.awt.Dimension(540, 80));

        // ===== Time Label =====
        JLabel timeCard = new JLabel(timeslot.getStart_time() + " - " + timeslot.getEnd_time());
        timeCard.setFont(new java.awt.Font("Heiti SC", 0, 20));
        coverCard.add(timeCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        // ===== Edit Button =====
        JButton editButton = new JButton();
        editButton.setBackground(new java.awt.Color(249, 249, 249));
        editButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/assets/images/edit fix.png")
        ));
        editButton.setBorder(null);
        editButton.setFocusPainted(false);
        editButton.setContentAreaFilled(false);
        coverCard.add(editButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 25, -1, -1));

        // ===== Delete Button =====
        JButton deleteButton = new JButton();
        deleteButton.setBackground(new java.awt.Color(249, 249, 249));
        deleteButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/assets/images/delete 6.png")
        ));
        deleteButton.setBorder(null);
        deleteButton.setFocusPainted(false);
        deleteButton.setContentAreaFilled(false);
        coverCard.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 25, -1, -1));

        // === EVENT LISTENER ===
        editButton.addActionListener(e -> {
            editedTimeSlot = timeslot;
            setupEditTimeslot(timeslot);
        });

        deleteButton.addActionListener(e -> {
            deleteTimeslot(timeslot);
        });

        return coverCard;
    }
    
    public void createTimeslot() {
        String start = inputStartTime.getText().trim();
        String end = inputEndTime.getText().trim();

        // VALIDASI NAMA
        if (start.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Jam mulai tidak boleh kosong!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // VALIDASI HARGA
        if (end.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Jam selesai tidak boleh kosong!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Integer startTime = Integer.parseInt(start);
        Integer endTime = Integer.parseInt(end);
        
        String formatedStartTime = formatTimeToString(startTime);
        String formatedEndTime = formatTimeToString(endTime);

        if (editedTimeSlot != null) {
            editTimeslot(editedTimeSlot.getId(), formatedStartTime, formatedEndTime);
            return;
        }

        Boolean res = timeSlotController.createTimeslot(formatedStartTime, formatedEndTime);

        if (res) {
            JOptionPane.showMessageDialog(
                    null,
                    "Timeslot berhasil ditambahkan!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE
            );
            inputEndTime.setText("");
            inputStartTime.setText("");
            setupTimeslot();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Lapangan gagal ditambahkan!",
                    "Gagal",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void setupEditTimeslot(TimeSlot t) {
        titleManageCourt.setText("Edit " + t.getStart_time() + " - " + t.getEnd_time());
        buttonCreate.setText("Simpan perubahan");

        inputStartTime.setText(formatStringToTimeInt(t.getStart_time()));
        inputEndTime.setText(formatStringToTimeInt(t.getEnd_time()));

        buttonBackToAdd.setVisible(true);
    }

    public void setupAddTimeslot() {
        titleManageCourt.setText("Tambah Lapangan ");
        buttonCreate.setText("Tambah Lapangan");

        inputStartTime.setText("");
        inputEndTime.setText("");

        buttonBackToAdd.setVisible(false);
    }

    public void editTimeslot(Integer id, String start, String end) {
        Boolean res = timeSlotController.editTimeslot(id, start, end);

        if (res) {
            JOptionPane.showMessageDialog(
                    null,
                    "Time Slot berhasil diperbaharui!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE
            );
            inputStartTime.setText("");
            inputEndTime.setText("");
            setupAddTimeslot();
            setupTimeslot();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Time Slot gagal ditambahkan!",
                    "Gagal",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void deleteTimeslot(TimeSlot t) {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Apakah Anda yakin ingin menghapus time slot pada pukul " + t.getStart_time()+ " - "+ t.getEnd_time() + "?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {

            boolean res = timeSlotController.deleteTimeslot(t.getId());

            if (res) {
                JOptionPane.showMessageDialog(
                        null,
                        "Timeslot berhasil dihapus!",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE
                );

                setupTimeslot(); // refresh tampilan
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Gagal menghapus timeslot!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public class NumericDocumentFilter extends DocumentFilter {

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr)
                throws BadLocationException {

            if (text.isEmpty() || text.matches("\\d+")) {
                super.insertString(fb, offset, text, attr);
            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {

            if (text.isEmpty() || text.matches("\\d+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    public void setNumericOnly(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new NumericDocumentFilter());
    }
    
    public String formatTimeToString(Integer time){
        if(time < 10){
            return "0" + time + ":00";
        }else{
            return time + ":00";
        }
    }
    
    public String formatStringToTimeInt(String time) {
        if (time == null || time.isEmpty()) {
            return null;
        }

        String hourPart = time.split(":")[0];

        return hourPart;
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
        SidebarLogoSeperator = new javax.swing.JPanel();
        sidebarDashboardButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        sidebarUsersButton1 = new javax.swing.JButton();
        sidebarUsersButton2 = new javax.swing.JButton();
        sidebarUsersButton4 = new javax.swing.JButton();
        sidebarUsersButton3 = new javax.swing.JButton();
        header = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        inputStartTime = new javax.swing.JTextField();
        inputEndTime = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        buttonCreate = new javax.swing.JButton();
        titleManageCourt = new javax.swing.JLabel();
        buttonBackToAdd = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainContainer = new javax.swing.JPanel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidebar.setBackground(new java.awt.Color(14, 60, 61));
        sidebar.setForeground(new java.awt.Color(14, 60, 61));
        sidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidebarLogo.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        sidebarLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/boombinton_logo.png"))); // NOI18N
        sidebarLogo.setText("BoomBinton");
        sidebar.add(sidebarLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 180, 40));

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

        sidebarUsersButton1.setBackground(new java.awt.Color(14, 60, 61));
        sidebarUsersButton1.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        sidebarUsersButton1.setForeground(new java.awt.Color(255, 255, 255));
        sidebarUsersButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/court-icon.png"))); // NOI18N
        sidebarUsersButton1.setText(" Court");
        sidebarUsersButton1.setBorder(null);
        sidebarUsersButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sidebarUsersButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidebarUsersButton1ActionPerformed(evt);
            }
        });
        sidebar.add(sidebarUsersButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 215, 180, 40));

        sidebarUsersButton2.setBackground(new java.awt.Color(14, 60, 61));
        sidebarUsersButton2.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        sidebarUsersButton2.setForeground(new java.awt.Color(255, 255, 255));
        sidebarUsersButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/users-icon.png"))); // NOI18N
        sidebarUsersButton2.setText(" Member");
        sidebarUsersButton2.setBorder(null);
        sidebarUsersButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sidebarUsersButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidebarUsersButton2ActionPerformed(evt);
            }
        });
        sidebar.add(sidebarUsersButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 180, 40));

        sidebarUsersButton4.setBackground(new java.awt.Color(14, 60, 61));
        sidebarUsersButton4.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        sidebarUsersButton4.setForeground(new java.awt.Color(255, 255, 255));
        sidebarUsersButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/history boombin.png"))); // NOI18N
        sidebarUsersButton4.setText(" History");
        sidebarUsersButton4.setBorder(null);
        sidebarUsersButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sidebarUsersButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidebarUsersButton4ActionPerformed(evt);
            }
        });
        sidebar.add(sidebarUsersButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 180, 40));

        sidebarUsersButton3.setBackground(new java.awt.Color(14, 60, 61));
        sidebarUsersButton3.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        sidebarUsersButton3.setForeground(new java.awt.Color(255, 255, 255));
        sidebarUsersButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/time_slots.png"))); // NOI18N
        sidebarUsersButton3.setText(" Timeslot");
        sidebarUsersButton3.setBorder(null);
        sidebarUsersButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sidebarUsersButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidebarUsersButton3ActionPerformed(evt);
            }
        });
        sidebar.add(sidebarUsersButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 180, 40));

        add(sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 720));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Heiti SC", 0, 24)); // NOI18N
        jLabel7.setText("Manajemen Time Slot");
        header.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel8.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Kelola data waktu booking disini");
        header.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 1060, 110));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inputStartTime.setBackground(new java.awt.Color(249, 249, 249));
        inputStartTime.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        inputStartTime.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 239, 239), 1, true));
        jPanel1.add(inputStartTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 95, 310, 40));

        inputEndTime.setBackground(new java.awt.Color(249, 249, 249));
        inputEndTime.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        inputEndTime.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 239, 239), 1, true));
        jPanel1.add(inputEndTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 185, 310, 40));

        jLabel9.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Waktu selesai");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        buttonCreate.setBackground(new java.awt.Color(15, 61, 62));
        buttonCreate.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        buttonCreate.setForeground(new java.awt.Color(255, 255, 255));
        buttonCreate.setText("Buat Time Slot");
        buttonCreate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51)));
        buttonCreate.setOpaque(true);
        buttonCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateActionPerformed(evt);
            }
        });
        jPanel1.add(buttonCreate, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 310, 50));

        titleManageCourt.setFont(new java.awt.Font("Heiti SC", 0, 20)); // NOI18N
        titleManageCourt.setText("Tambah Time Slot");
        jPanel1.add(titleManageCourt, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        buttonBackToAdd.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        buttonBackToAdd.setText("X");
        buttonBackToAdd.setPreferredSize(new java.awt.Dimension(20, 20));
        buttonBackToAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBackToAddActionPerformed(evt);
            }
        });
        jPanel1.add(buttonBackToAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, -1, -1));

        jLabel10.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Waktu mulai");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 370, 340));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(620, 560));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Heiti SC", 0, 20)); // NOI18N
        jLabel5.setText("Data Time Slot");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(620, 560));

        mainContainer.setBackground(new java.awt.Color(255, 255, 255));
        mainContainer.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 30, 0, 30));
        mainContainer.setAutoscrolls(true);
        mainContainer.setPreferredSize(new java.awt.Dimension(0, 0));
        mainContainer.setSize(new java.awt.Dimension(560, 0));
        jScrollPane1.setViewportView(mainContainer);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, -1, 500));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 130, -1, 560));
    }// </editor-fold>//GEN-END:initComponents

    private void sidebarDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarDashboardButtonActionPerformed
        dashboardFrame.showCourtPanel();
    }//GEN-LAST:event_sidebarDashboardButtonActionPerformed

    private void sidebarUsersButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarUsersButton1ActionPerformed
        // TODO add your handling code here:
        dashboardFrame.showManageCourtPanel();
    }//GEN-LAST:event_sidebarUsersButton1ActionPerformed

    private void sidebarUsersButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarUsersButton2ActionPerformed
        // TODO add your handling code here:
        dashboardFrame.showMemberPanel();
    }//GEN-LAST:event_sidebarUsersButton2ActionPerformed

    private void sidebarUsersButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarUsersButton4ActionPerformed
        // TODO add your handling code here:
        dashboardFrame.showHistoryPanel();
    }//GEN-LAST:event_sidebarUsersButton4ActionPerformed

    private void buttonCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateActionPerformed
        // TODO add your handling code here:
        createTimeslot();
    }//GEN-LAST:event_buttonCreateActionPerformed

    private void buttonBackToAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBackToAddActionPerformed
        // TODO add your handling code here:
        setupAddTimeslot();
    }//GEN-LAST:event_buttonBackToAddActionPerformed

    private void sidebarUsersButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarUsersButton3ActionPerformed
        // TODO add your handling code here:
        dashboardFrame.showTimeslotPanel();
    }//GEN-LAST:event_sidebarUsersButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SidebarLogoSeperator;
    private javax.swing.JButton buttonBackToAdd;
    private javax.swing.JButton buttonCreate;
    private javax.swing.JPanel header;
    private javax.swing.JTextField inputEndTime;
    private javax.swing.JTextField inputStartTime;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainContainer;
    private javax.swing.JPanel sidebar;
    private javax.swing.JButton sidebarDashboardButton;
    private javax.swing.JLabel sidebarLogo;
    private javax.swing.JButton sidebarUsersButton1;
    private javax.swing.JButton sidebarUsersButton2;
    private javax.swing.JButton sidebarUsersButton3;
    private javax.swing.JButton sidebarUsersButton4;
    private javax.swing.JLabel titleManageCourt;
    // End of variables declaration//GEN-END:variables
}
