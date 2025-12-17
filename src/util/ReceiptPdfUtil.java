package util;

import model.Court;
import model.TimeSlot;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Utility untuk generate struk PDF booking lapangan
 * 
 * @author gungwira
 */
public class ReceiptPdfUtil {
    
    // Konstanta untuk styling
    private static final float MARGIN = 30;
    private static final float PAGE_WIDTH = PDRectangle.A6.getWidth();
    
    /**
     * Generate struk booking dalam format PDF
     */
    public static void generateReceipt(
            Court court,
            List<TimeSlot> slots,
            LocalDate date,
            String customerName,
            String customerPhone
    ) {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A6);
            doc.addPage(page);
            
            PDPageContentStream cs = new PDPageContentStream(doc, page);
            
            float y = 380; // Starting Y position
            
            // ========== LOGO & HEADER ==========
            y = drawLogo(cs, y);
            y -= 15;
            
            y = drawHeader(cs, y);
            y -= 20;
            
            // ========== SEPARATOR LINE ==========
            drawLine(cs, MARGIN, y, PAGE_WIDTH - MARGIN, y);
            y -= 20;
            
            // ========== BOOKING INFO ==========
            y = drawBookingInfo(cs, y, court, date, customerName, customerPhone);
            y -= 15;
            
            // ========== SEPARATOR LINE ==========
            drawLine(cs, MARGIN, y, PAGE_WIDTH - MARGIN, y);
            y -= 20;
            
            // ========== TIME SLOTS & PRICE ==========
            y = drawTimeSlots(cs, y, slots, court);
            y -= 10;
            
            // ========== SEPARATOR LINE ==========
            drawLine(cs, MARGIN, y, PAGE_WIDTH - MARGIN, y);
            y -= 20;
            
            // ========== TOTAL ==========
            int total = slots.size() * court.getPrice_per_hour();
            y = drawTotal(cs, y, slots.size(), total);
            y -= 25;
            
            // ========== SEPARATOR LINE ==========
            drawDashedLine(cs, MARGIN, y, PAGE_WIDTH - MARGIN, y);
            y -= 20;
            
            // ========== FOOTER ==========
            drawFooter(cs, y);
            
            cs.close();
            
            // Save with timestamp filename
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            String filename = "struk-booking-" + timestamp + ".pdf";
            doc.save(filename);
            
            // Tampilkan dialog sukses
            JOptionPane.showMessageDialog(
                    null,
                    "Struk berhasil disimpan!\n\nFile: " + filename + "\n\nTotal: Rp " + formatRupiah(total),
                    "✅ Pembayaran Berhasil",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            // Auto open PDF (optional)
            openPDF(filename);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Gagal membuat struk!\n\nError: " + e.getMessage(),
                    "❌ Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Draw logo (jika ada)
     */
    private static float drawLogo(PDPageContentStream cs, float y) throws IOException {
        try {
            // Coba load logo dari assets/images/logo.png
            File logoFile = new File("src/assets/images/logo.png");
            
            if (logoFile.exists()) {
                PDImageXObject logo = PDImageXObject.createFromFile(
                    logoFile.getAbsolutePath(), 
                    (PDDocument) cs.getClass().getMethod("getDocument").invoke(cs)
                );
                
                // Center logo
                float logoWidth = 60;
                float logoHeight = 60;
                float logoX = (PAGE_WIDTH - logoWidth) / 2;
                
                cs.drawImage(logo, logoX, y - logoHeight, logoWidth, logoHeight);
                
                return y - logoHeight - 10;
            }
        } catch (Exception e) {
            // Logo not found, skip
            System.out.println("Logo tidak ditemukan, skip...");
        }
        
        return y;
    }
    
    private static float drawHeader(PDPageContentStream cs, float y) throws IOException {
        // Nama toko
        drawCenteredText(cs, "BOOMBINTON", y, 
            new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
        y -= 15;
        
        // Subtitle
        drawCenteredText(cs, "Badminton Court Rental", y,
            new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
        y -= 12;
        
        // Alamat
        drawCenteredText(cs, "Jl. Olahraga No. 123, Surabaya", y,
            new PDType1Font(Standard14Fonts.FontName.HELVETICA), 8);
        y -= 10;
        
        // Telp
        drawCenteredText(cs, "Telp: +62 123 456 7890", y,
            new PDType1Font(Standard14Fonts.FontName.HELVETICA), 8);
        y -= 5;
        
        return y;
    }
    
    private static float drawBookingInfo(PDPageContentStream cs, float y, 
                                         Court court, LocalDate date,
                                         String customerName, String customerPhone) throws IOException {
        cs.beginText();
        cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
        cs.newLineAtOffset(MARGIN, y);
        
        cs.showText("Tanggal    : " + DateUtils.formatIndonesian(date));
        cs.newLineAtOffset(0, -12);
        cs.showText("Waktu      : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        cs.newLineAtOffset(0, -12);
        cs.showText("Lapangan   : " + court.getName());
        cs.newLineAtOffset(0, -12);
        cs.showText("Pelanggan  : " + customerName);
        cs.newLineAtOffset(0, -12);
        cs.showText("No. HP     : " + customerPhone);
        
        cs.endText();
        
        return y - 60;
    }
    
    private static float drawTimeSlots(PDPageContentStream cs, float y, 
                                        List<TimeSlot> slots, Court court) throws IOException {
        cs.beginText();
        cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 9);
        cs.newLineAtOffset(MARGIN, y);
        cs.showText("Jam Booking");
        cs.newLineAtOffset(100, 0);
        cs.showText("Harga");
        cs.endText();
        
        y -= 15;
        
        for (TimeSlot slot : slots) {
            cs.beginText();
            cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
            cs.newLineAtOffset(MARGIN, y);
            
            String timeStr = formatTime(slot.getStart_time()) + " - " + formatTime(slot.getEnd_time());
            cs.showText(timeStr);
            
            cs.newLineAtOffset(100, 0);
            cs.showText(formatRupiah(court.getPrice_per_hour()));
            
            cs.endText();
            y -= 12;
        }
        
        return y;
    }
    

    private static float drawTotal(PDPageContentStream cs, float y, 
                                    int jumlahJam, int total) throws IOException {
        cs.beginText();
        cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
        cs.newLineAtOffset(MARGIN, y);
        cs.showText("Jumlah Jam : " + jumlahJam + " jam");
        cs.endText();
        
        y -= 15;
        
        cs.beginText();
        cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 11);
        cs.newLineAtOffset(MARGIN, y);
        cs.showText("TOTAL");
        cs.newLineAtOffset(100, 0);
        cs.showText(formatRupiah(total));
        cs.endText();
        
        return y;
    }
    
    private static void drawFooter(PDPageContentStream cs, float y) throws IOException {
        y -= 5;
        
        drawCenteredText(cs, "Terima kasih telah booking!", y,
            new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 9);
        y -= 12;
        
        drawCenteredText(cs, "Silakan datang 10 menit sebelum jadwal", y,
            new PDType1Font(Standard14Fonts.FontName.HELVETICA), 8);
        y -= 10;
        
        drawCenteredText(cs, "Harap tunjukkan struk ini saat check-in", y,
            new PDType1Font(Standard14Fonts.FontName.HELVETICA), 8);
        y -= 15;
        
        drawCenteredText(cs, "====================", y,
            new PDType1Font(Standard14Fonts.FontName.HELVETICA), 8);
        y -= 10;
        
        drawCenteredText(cs, "Follow us: @boombinton_sby", y,
            new PDType1Font(Standard14Fonts.FontName.HELVETICA), 7);
    }
    
    
    private static void drawCenteredText(PDPageContentStream cs, String text, float y,
                                         PDType1Font font, float fontSize) throws IOException {
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;
        float x = (PAGE_WIDTH - textWidth) / 2;
        
        cs.beginText();
        cs.setFont(font, fontSize);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }
    
    private static void drawLine(PDPageContentStream cs, float x1, float y, float x2, float y2) throws IOException {
        cs.moveTo(x1, y);
        cs.lineTo(x2, y2);
        cs.stroke();
    }
    
    private static void drawDashedLine(PDPageContentStream cs, float x1, float y, float x2, float y2) throws IOException {
        cs.setLineDashPattern(new float[]{3, 3}, 0);
        cs.moveTo(x1, y);
        cs.lineTo(x2, y2);
        cs.stroke();
        cs.setLineDashPattern(new float[]{}, 0); // Reset to solid
    }
    
    private static String formatRupiah(int amount) {
        return String.format("Rp %,d", amount).replace(',', '.');
    }
    
    private static String formatTime(String time) {
        if (time != null && time.length() >= 5) {
            return time.substring(0, 5);
        }
        return time;
    }
    
    private static void openPDF(String filename) {
        try {
            File file = new File(filename);
            if (file.exists()) {
                // Open with default PDF viewer
                if (java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop.getDesktop().open(file);
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal membuka PDF: " + e.getMessage());
        }
    }
}