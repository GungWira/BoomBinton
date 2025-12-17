/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.dashboard;

import controller.BookingController;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Booking;
import util.DateUtils;

/**
 *
 * @author gungwira
 */
public class HistoryPanel extends javax.swing.JPanel {
    
    private DashboardFrame dashboardFrame;
    private BookingController bookingController;
    /**
     * Creates new form HistoryPanel
     */
    public HistoryPanel(DashboardFrame dashboardFrame) {
        this.dashboardFrame = dashboardFrame;
        bookingController = new BookingController();
        
        initComponents();
        
        setSize(1280, 754);
    }
    
    public void setupHistoryDaily(){
        mainContainer.removeAll();
        
        titlePage.setText("Data Histori Transaksi (Harian)");
        
        List<Booking> bookings = bookingController.getTodayBookings();
        
        for (Booking booking : bookings){
            JPanel card = createOrderCard(booking);
            mainContainer.add(card);
        }
        
        int cardHeight = 110;
        int spacing = 5;
        int totalHeight = (bookings.size() * cardHeight) + ((bookings.size() - 1) * spacing) + 20;

        mainContainer.setPreferredSize(new Dimension(560, totalHeight));
        
        mainContainer.revalidate();
        mainContainer.repaint();
    }
    
    public void setupHistoryMonthly(){
        mainContainer.removeAll();
        
        titlePage.setText("Data Histori Transaksi (Bulanan)");
        
        List<Booking> bookings = bookingController.getMonthlyBookings();
        
        for (Booking booking : bookings){
            JPanel card = createOrderCard(booking);
            mainContainer.add(card);
        }
        
        int cardHeight = 110;
        int spacing = 5;
        int totalHeight = (bookings.size() * cardHeight) + ((bookings.size() - 1) * spacing) + 20;

        mainContainer.setPreferredSize(new Dimension(560, totalHeight));
        
        mainContainer.revalidate();
        mainContainer.repaint();
    }
    
    public void setupHistoryYearly(){
        mainContainer.removeAll();
        
        titlePage.setText("Data Histori Transaksi (Tahunan)");
        
        List<Booking> bookings = bookingController.getYearlyBookings();
        
        for (Booking booking : bookings){
            JPanel card = createOrderCard(booking);
            mainContainer.add(card);
        }
        
        int cardHeight = 110;
        int spacing = 5;
        int totalHeight = (bookings.size() * cardHeight) + ((bookings.size() - 1) * spacing) + 20;

        mainContainer.setPreferredSize(new Dimension(560, totalHeight));
        
        mainContainer.revalidate();
        mainContainer.repaint();
    }
    
    public JPanel createOrderCard(Booking booking) {

        JPanel card = new JPanel();
        card.setBackground(new java.awt.Color(249, 249, 249));
        card.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        card.setPreferredSize(new java.awt.Dimension(950, 110)); // WAJIB agar rapi

        // ===== Nama Lapangan =====
        JLabel namaLapangan = new JLabel(booking.getCourtName());
        namaLapangan.setFont(new java.awt.Font("Heiti SC", 0, 20));
        card.add(namaLapangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        // ===== Waktu Lapangan =====
        JLabel waktuLapangan = new JLabel(booking.getStartTime() + " - " + booking.getEndTime());
        waktuLapangan.setFont(new java.awt.Font("Heiti SC", 0, 16));
        waktuLapangan.setForeground(new java.awt.Color(102, 102, 102));
        card.add(waktuLapangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        // ===== Nama Pengorder =====
        JLabel namaPengorder = new JLabel(booking.getName());
        namaPengorder.setFont(new java.awt.Font("Heiti SC", 0, 20));
        card.add(namaPengorder, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, -1, -1));

        // ===== Phone Pengorder =====
        JLabel phonePengorder = new JLabel(booking.getPhone());
        phonePengorder.setFont(new java.awt.Font("Heiti SC", 0, 16));
        phonePengorder.setForeground(new java.awt.Color(102, 102, 102));
        card.add(phonePengorder, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, -1, -1));

        // ===== Price =====
        JLabel priceOrder = new JLabel(formatRupiah(booking.getPrice()));
        priceOrder.setFont(new java.awt.Font("Heiti SC", 0, 20));
        priceOrder.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        card.add(priceOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 30, 210, -1));

        // ===== Tanggal Order =====
        JLabel tanggalOrder = new JLabel(DateUtils.formatIndonesian(booking.getBooking_date().toLocalDate()));
        tanggalOrder.setFont(new java.awt.Font("Heiti SC", 0, 16));
        tanggalOrder.setForeground(new java.awt.Color(102, 102, 102));
        tanggalOrder.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        card.add(tanggalOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(651, 60, 280, -1));

        return card;
    }
    
    private static String formatRupiah(int amount) {
        return String.format("Rp %,d", amount).replace(',', '.');
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
        sidebarUsersButton3 = new javax.swing.JButton();
        header = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainContainer = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        titlePage = new javax.swing.JLabel();

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
        sidebarUsersButton1.setText(" History");
        sidebarUsersButton1.setBorder(null);
        sidebarUsersButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sidebarUsersButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidebarUsersButton1ActionPerformed(evt);
            }
        });
        sidebar.add(sidebarUsersButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 180, 40));

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

        sidebarUsersButton3.setBackground(new java.awt.Color(14, 60, 61));
        sidebarUsersButton3.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        sidebarUsersButton3.setForeground(new java.awt.Color(255, 255, 255));
        sidebarUsersButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/court-icon.png"))); // NOI18N
        sidebarUsersButton3.setText(" Court");
        sidebarUsersButton3.setBorder(null);
        sidebarUsersButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sidebarUsersButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidebarUsersButton3ActionPerformed(evt);
            }
        });
        sidebar.add(sidebarUsersButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 215, 180, 40));

        add(sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 720));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Heiti SC", 0, 24)); // NOI18N
        jLabel7.setText("Histori Transaksi");
        header.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel8.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Data histori transaksi lapangan");
        header.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 1060, 110));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(620, 560));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1020, 480));

        jButton1.setBackground(new java.awt.Color(0, 51, 51));
        jButton1.setFont(new java.awt.Font("Heiti SC", 0, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Hari ini");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setOpaque(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 120, 40));

        jButton2.setBackground(new java.awt.Color(0, 51, 51));
        jButton2.setFont(new java.awt.Font("Heiti SC", 0, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Tahun ini");
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setOpaque(true);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 20, 120, 40));

        jButton3.setBackground(new java.awt.Color(0, 51, 51));
        jButton3.setFont(new java.awt.Font("Heiti SC", 0, 15)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Bulan ini");
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setOpaque(true);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, 120, 40));

        titlePage.setFont(new java.awt.Font("Heiti SC", 0, 20)); // NOI18N
        titlePage.setText("Data Histori Transaksi (Harian)");
        jPanel2.add(titlePage, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 1020, 560));
    }// </editor-fold>//GEN-END:initComponents

    private void sidebarDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarDashboardButtonActionPerformed
        dashboardFrame.showCourtPanel();
    }//GEN-LAST:event_sidebarDashboardButtonActionPerformed

    private void sidebarUsersButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarUsersButton1ActionPerformed
        // TODO add your handling code here:
        dashboardFrame.showHistoryPanel();
    }//GEN-LAST:event_sidebarUsersButton1ActionPerformed

    private void sidebarUsersButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarUsersButton2ActionPerformed
        // TODO add your handling code here:
        dashboardFrame.showMemberPanel();
    }//GEN-LAST:event_sidebarUsersButton2ActionPerformed

    private void sidebarUsersButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarUsersButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sidebarUsersButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setupHistoryDaily();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        setupHistoryYearly();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        setupHistoryMonthly();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SidebarLogoSeperator;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainContainer;
    private javax.swing.JPanel sidebar;
    private javax.swing.JButton sidebarDashboardButton;
    private javax.swing.JLabel sidebarLogo;
    private javax.swing.JButton sidebarUsersButton1;
    private javax.swing.JButton sidebarUsersButton2;
    private javax.swing.JButton sidebarUsersButton3;
    private javax.swing.JLabel titlePage;
    // End of variables declaration//GEN-END:variables
}
