/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.dashboard;

import controller.CourtController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.Court;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.*;

/**
 *
 * @author gungwira
 */
public class ManageCourtPanel extends javax.swing.JPanel {

    final private DashboardFrame dashboardFrame;
    final private CourtController courtController;
    private Court editedCourt;

    /**
     * Creates new form ManageCourtPanel
     */
    public ManageCourtPanel(DashboardFrame dashboardFrame) {
        this.dashboardFrame = dashboardFrame;
        this.courtController = new CourtController();
        this.editedCourt = null;

        initComponents();

        setSize(1280, 754);

        setupCourt();
    }

    public void setupCourt() {
//        mainContainer.setLayout(new GridLayout(0, 1, 5, 5));

        mainContainer.removeAll();
        List<Court> courts = courtController.getAllCourts();

        for (Court court : courts) {
            JPanel card = createCourtCard(court);
            mainContainer.add(card);
        }

//        mainContainer.setPreferredSize(null);
        mainContainer.revalidate();
        mainContainer.repaint();

        buttonCreate.setContentAreaFilled(false);
        buttonCreate.setOpaque(true);

        setNumericOnly(inputPrice);

        buttonBackToAdd.setVisible(false);
        editedCourt = null;

    }

    public JPanel createCourtCard(Court court) {

        // === Data ===
        String name = court.getName();
        String detail = court.getStatus() + " - Rp " + court.getPrice_per_hour() + "/jam";

        // === Container Card ===
        JPanel containerCard = new JPanel();
        containerCard.setBackground(new Color(249, 249, 249));
        containerCard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        containerCard.setPreferredSize(new Dimension(560, 100)); // sesuaikan tinggi card

        // === Title ===
        JLabel cardTitle = new JLabel(name);
        cardTitle.setFont(new Font("Heiti SC", Font.PLAIN, 18));
        containerCard.add(cardTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        // === Detail ===
        JLabel cardDetail = new JLabel(detail);
        cardDetail.setFont(new Font("Heiti SC", Font.PLAIN, 14));
        cardDetail.setForeground(new Color(102, 102, 102));
        containerCard.add(cardDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, -1, -1));

        // === Edit Button ===
        JButton cardEditButton = new JButton();
        cardEditButton.setBackground(new Color(249, 249, 249));
        cardEditButton.setBorder(null);
        cardEditButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/edit fix.png")));
        containerCard.add(cardEditButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, -1, -1));

        // === Delete Button ===
        JButton cardDeleteButton = new JButton();
        cardDeleteButton.setBackground(new Color(249, 249, 249));
        cardDeleteButton.setBorder(null);
        cardDeleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/delete 6.png")));
        containerCard.add(cardDeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, -1, -1));

        // === EVENT LISTENER ===
        cardEditButton.addActionListener(e -> {
            editedCourt = court;
            setupEditCourt(court);
        });

        cardDeleteButton.addActionListener(e -> {
            deleteCourt(court);
        });

        return containerCard;
    }

    public void createCourt() {
        String name = inputName.getText();
        String priceText = inputPrice.getText().trim();
        Boolean isChecked = checkBoxAktif.isSelected();

        // VALIDASI NAMA
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Nama lapangan tidak boleh kosong!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // VALIDASI HARGA
        if (priceText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Harga tidak boleh kosong!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Integer price = Integer.parseInt(priceText);

        String status = isChecked ? "Active" : "Deactive";

        if (editedCourt != null) {
            editCourt(editedCourt.getId(), name, price, status);
            return;
        }

        Boolean res = courtController.createCourt(name, price, status);

        if (res) {
            JOptionPane.showMessageDialog(
                    null,
                    "Lapangan berhasil ditambahkan!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE
            );
            inputName.setText("");
            inputPrice.setText("");
            setupCourt();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Lapangan gagal ditambahkan!",
                    "Gagal",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void setupEditCourt(Court c) {
        titleManageCourt.setText("Edit " + c.getName());
        buttonCreate.setText("Simpan perubahan");

        System.out.println("Status : " + c.getStatus());

        checkBoxAktif.setSelected(c.getStatus().equals("Active"));

        inputName.setText(c.getName());
        inputPrice.setText(String.valueOf(c.getPrice_per_hour()));

        buttonBackToAdd.setVisible(true);
    }

    public void setupAddCourt() {
        titleManageCourt.setText("Tambah Lapangan ");
        buttonCreate.setText("Tambah Lapangan");

        inputName.setText("");
        inputPrice.setText("");
        checkBoxAktif.setSelected(false);

        buttonBackToAdd.setVisible(false);
    }

    public void editCourt(Integer id, String name, Integer price, String status) {
        Boolean res = courtController.editCourt(id, name, price, status);

        if (res) {
            JOptionPane.showMessageDialog(
                    null,
                    "Lapangan berhasil diperbaharui!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE
            );
            inputName.setText("");
            inputPrice.setText("");
            setupAddCourt();
            setupCourt();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Lapangan gagal ditambahkan!",
                    "Gagal",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void deleteCourt(Court c) {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Apakah Anda yakin ingin menghapus lapangan \"" + c.getName() + "\"?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {

            boolean res = courtController.deleteCourt(c.getId());

            if (res) {
                JOptionPane.showMessageDialog(
                        null,
                        "Lapangan berhasil dihapus!",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE
                );

                setupCourt(); // refresh tampilan
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Gagal menghapus lapangan!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public class NumericDocumentFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                throws BadLocationException {

            if (text.isEmpty() || text.matches("\\d+")) {
                super.insertString(fb, offset, text, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {

            if (text.isEmpty() || text.matches("\\d+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    public void setNumericOnly(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new NumericDocumentFilter());
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
        header = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        inputName = new javax.swing.JTextField();
        inputPrice = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        checkBoxAktif = new javax.swing.JCheckBox();
        buttonCreate = new javax.swing.JButton();
        titleManageCourt = new javax.swing.JLabel();
        buttonBackToAdd = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainContainer = new javax.swing.JPanel();

        setBackground(new java.awt.Color(239, 239, 239));
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

        add(sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 720));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Heiti SC", 0, 24)); // NOI18N
        jLabel7.setText("Manajemen Lapangan");
        header.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel8.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Kelola data lapangan disini");
        header.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 1060, 110));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inputName.setBackground(new java.awt.Color(249, 249, 249));
        inputName.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        inputName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 239, 239), 1, true));
        jPanel1.add(inputName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 95, 310, 40));

        inputPrice.setBackground(new java.awt.Color(249, 249, 249));
        inputPrice.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        inputPrice.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 239, 239), 1, true));
        jPanel1.add(inputPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 185, 310, 40));

        jLabel9.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Harga sewa");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        checkBoxAktif.setFont(new java.awt.Font("Heiti SC", 0, 14)); // NOI18N
        checkBoxAktif.setForeground(new java.awt.Color(102, 102, 102));
        checkBoxAktif.setText("Aktifkan lapangan");
        checkBoxAktif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxAktifActionPerformed(evt);
            }
        });
        jPanel1.add(checkBoxAktif, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, -1));

        buttonCreate.setBackground(new java.awt.Color(15, 61, 62));
        buttonCreate.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        buttonCreate.setForeground(new java.awt.Color(255, 255, 255));
        buttonCreate.setText("Buat Lapangan");
        buttonCreate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51)));
        buttonCreate.setOpaque(true);
        buttonCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateActionPerformed(evt);
            }
        });
        jPanel1.add(buttonCreate, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 310, 50));

        titleManageCourt.setFont(new java.awt.Font("Heiti SC", 0, 20)); // NOI18N
        titleManageCourt.setText("Tambah Lapangan");
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
        jLabel10.setText("Nama lapangan");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 370, 370));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(620, 560));
        jPanel2.setMinimumSize(new java.awt.Dimension(187, 560));
        jPanel2.setPreferredSize(new java.awt.Dimension(620, 560));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Heiti SC", 0, 20)); // NOI18N
        jLabel5.setText("Data Lapangan");
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

    private void checkBoxAktifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxAktifActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxAktifActionPerformed

    private void buttonCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateActionPerformed
        // TODO add your handling code here:
        createCourt();
    }//GEN-LAST:event_buttonCreateActionPerformed

    private void buttonBackToAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBackToAddActionPerformed
        // TODO add your handling code here:
        setupAddCourt();
    }//GEN-LAST:event_buttonBackToAddActionPerformed

    private void sidebarUsersButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarUsersButton1ActionPerformed
        // TODO add your handling code here:
        dashboardFrame.showManageCourtPanel();
    }//GEN-LAST:event_sidebarUsersButton1ActionPerformed

    private void sidebarUsersButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarUsersButton2ActionPerformed
        // TODO add your handling code here:
        dashboardFrame.showMemberPanel();
    }//GEN-LAST:event_sidebarUsersButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SidebarLogoSeperator;
    private javax.swing.JButton buttonBackToAdd;
    private javax.swing.JButton buttonCreate;
    private javax.swing.JCheckBox checkBoxAktif;
    private javax.swing.JPanel header;
    private javax.swing.JTextField inputName;
    private javax.swing.JTextField inputPrice;
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
    private javax.swing.JLabel titleManageCourt;
    // End of variables declaration//GEN-END:variables
}
