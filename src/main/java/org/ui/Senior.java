package org.ui;

import org.example.Hotelutil;
import org.ui_master_data.HotelTableModel;
import org.ui_master_data.MainFrame;
import org.ui_transactional_data.MainFrame_transaction;

import javax.swing.*;
import java.awt.*;
import javax.swing.JOptionPane;

public class Senior extends javax.swing.JFrame {
    public Senior() {
        super("Senior");
        setSize(400,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        setLayout(flowLayout);
        JButton master = new JButton("Master Data");
        master.addActionListener(e->{
            try {
                new MainFrame().setVisible(true);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, "Failed to open master frame: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw ex;
            }
            dispose();
        });
        add(master);
        JButton transactional = new JButton("Transactional Data");
        transactional.addActionListener(e->{
            try {
                new MainFrame_transaction().setVisible(true);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, "Failed to open transactional frame: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw ex;
            }
            dispose();
        });
        add(transactional);
    }
}
