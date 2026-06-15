package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class App extends JFrame {

    private final OccupanciesDAO occupanciesDAO;
    private final JComboBox<String> monthBox;
    private final JComboBox<String> yearBox;

    public App() {
        setTitle("Hotelauslastung Statistik");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        occupanciesDAO = new OccupanciesDAO();

        // controls for month statistics
        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        monthBox = new JComboBox<>(months);
        monthBox.setSelectedItem(String.format("%02d", LocalDate.now().getMonthValue()));

        yearBox = new JComboBox<>();
        this.populateYearsFromDatabase(yearBox);
        if (yearBox.getItemCount() > 0) {
            yearBox.setSelectedItem(String.valueOf(LocalDate.now().getYear()));
        }

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(new JLabel("Monat:"));
        controlPanel.add(monthBox);
        controlPanel.add(new JLabel("Jahr:"));
        controlPanel.add(yearBox);

        JButton statsButton = new JButton("Monatsstatistik PDF erstellen");
        statsButton.addActionListener(e -> this.createMonthlyStatsPdf());

        add(new JScrollPane(createStatsTable()), BorderLayout.CENTER);
        JPanel south = new JPanel(new BorderLayout());
        south.add(controlPanel, BorderLayout.WEST);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.add(statsButton);
        south.add(right, BorderLayout.EAST);
        add(south, BorderLayout.SOUTH);
    }

    // Create a statistics table from database
    private JTable createStatsTable() {
        // Fetch all occupancy data and populate table model
        List<occupancies> allData = occupanciesDAO.findAll();

        String[] columns = {"Hotel ID", "Zimmer", "Genutzt", "Betten", "Genutzt", "Jahr", "Monat"};
        Object[][] data = new Object[allData.size()][7];

        // Convert each occupancy record to a row in the table
        for (int i = 0; i < allData.size(); i++) {
            occupancies occ = allData.get(i);
            data[i] = new Object[]{occ.getId(), occ.getRoom(), occ.getUsedrooms(),
                                    occ.getBeds(), occ.getUsedbeds(), occ.getYear(), occ.getMonth()};
        }

        return new JTable(new DefaultTableModel(data, columns));
    }

    // Gather available years from database
    private void populateYearsFromDatabase(JComboBox<String> yearBox) {
        // Extract unique years from all occupancy records in the database
        List<occupancies> allData = occupanciesDAO.findAll();
        Set<String> years = new TreeSet<>();

        for (occupancies occ : allData) {
            years.add(String.valueOf(occ.getYear()));
        }

        // Add collected years to the combo box
        for (String year : years) {
            yearBox.addItem(year);
        }

        // Fallback: add current year if no data exists
        if (yearBox.getItemCount() == 0) {
            yearBox.addItem(String.valueOf(LocalDate.now().getYear()));
        }
    }

    private void createMonthlyStatsPdf() {
        String selectedMonth = (String) monthBox.getSelectedItem();
        String selectedYear = (String) yearBox.getSelectedItem();
        if (selectedMonth == null || selectedYear == null) {
            JOptionPane.showMessageDialog(this, "Bitte Monat und Jahr auswählen.");
            return;
        }

        int month = Integer.parseInt(selectedMonth);
        int year = Integer.parseInt(selectedYear);

        // Calculate summary statistics for the selected month
        Stats stats = this.computeStatsFromDatabase(year, month);

        // Create PDF document with occupancy statistics
        File file = new File(String.format("monatsstatistik_%d_%02d.pdf", year, month));
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDType1Font bold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                float x = 60;
                float y = 750;

                // Write PDF header and summary section
                writeText(cs, bold, 18, x, y, "Monatsstatistik Hotelauslastung");
                y -= 30;
                writeText(cs, font, 12, x, y, String.format("Zeitraum: %02d/%d", month, year));
                y -= 25;

                writeText(cs, bold, 12, x, y, "Zusammenfassung:");
                y -= 20;

                // Display aggregated statistics
                writeText(cs, font, 11, x, y, "Anzahl Hotels/Einträge: " + stats.records);
                y -= 18;
                writeText(cs, font, 11, x, y, "Summe Zimmer: " + stats.totalRooms);
                y -= 18;
                writeText(cs, font, 11, x, y, "Genutzte Zimmer: " + stats.totalUsedRooms + " (" + String.format("%.2f", stats.roomOccupancyPercent) + "%)");
                y -= 18;
                writeText(cs, font, 11, x, y, "Summe Betten: " + stats.totalBeds);
                y -= 18;
                writeText(cs, font, 11, x, y, "Genutzte Betten: " + stats.totalUsedBeds + " (" + String.format("%.2f", stats.bedOccupancyPercent) + "%)");
                y -= 25;

                writeText(cs, bold, 12, x, y, "Details pro Hotel:");
                y -= 18;

                // Write hotel-by-hotel breakdown with occupancy percentages
                List<occupancies> monthData = getMonthlyData(year, month);
                for (occupancies occ : monthData) {
                    double roomPercent = occ.getRoom() > 0 ? 100.0 * occ.getUsedrooms() / occ.getRoom() : 0;
                    double bedPercent = occ.getBeds() > 0 ? 100.0 * occ.getUsedbeds() / occ.getBeds() : 0;
                    String detail = String.format("Hotel %d: %d/%d Zimmer (%.1f%%), %d/%d Betten (%.1f%%)",
                            occ.getId(), occ.getUsedrooms(), occ.getRoom(), roomPercent,
                            occ.getUsedbeds(), occ.getBeds(), bedPercent);
                    writeText(cs, font, 10, x, y, detail);
                    y -= 15;
                    if (y < 50) break; // Stop if we run out of space on current page
                }
            }

            doc.save(file);
            JOptionPane.showMessageDialog(this, "PDF erstellt: " + file.getAbsolutePath());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fehler: " + ex.getMessage());
        }
    }

    private Stats computeStatsFromDatabase(int year, int month) {
        Stats s = new Stats();
        // Fetch all occupancy records for the given month/year
        List<occupancies> monthData = this.getMonthlyData(year, month);

        // Aggregate room and bed occupancy statistics
        for (occupancies occ : monthData) {
            s.records++;
            s.totalRooms += occ.getRoom();
            s.totalUsedRooms += occ.getUsedrooms();
            s.totalBeds += occ.getBeds();
            s.totalUsedBeds += occ.getUsedbeds();
        }

        // Calculate occupancy percentages
        if (s.totalRooms > 0) s.roomOccupancyPercent = 100.0 * s.totalUsedRooms / s.totalRooms;
        if (s.totalBeds > 0) s.bedOccupancyPercent = 100.0 * s.totalUsedBeds / s.totalBeds;
        return s;
    }

    private List<occupancies> getMonthlyData(int year, int month) {
        // Filter all occupancy data to get records for specific month/year
        List<occupancies> allData = occupanciesDAO.findAll();
        return allData.stream()
                .filter(occ -> occ.getYear() == year && occ.getMonth() == month)
                .toList();
    }

    private static class Stats {
        int records = 0;
        long totalRooms = 0;
        long totalUsedRooms = 0;
        long totalBeds = 0;
        long totalUsedBeds = 0;
        double roomOccupancyPercent = 0.0;
        double bedOccupancyPercent = 0.0;
    }

    private void writeText(PDPageContentStream cs, PDType1Font font, int size,
                           float x, float y, String text) throws Exception {
        cs.beginText();
        cs.setFont(font, size);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }
    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
    */

}
