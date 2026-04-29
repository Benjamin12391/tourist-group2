package org.ui;

import org.example.Hotelutil;
import org.ui_master_data.HotelTableModel;
import org.ui_master_data.MainFrame;
import org.ui_transactional_data.MainFrame_transaction;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

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
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });
        add(master);
        JButton transactional = new JButton("Transactional Data");
        transactional.addActionListener(e->{
            try {
                new MainFrame_transaction().setVisible(true);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);

            }
            dispose();
        });
        add(transactional);
    }
}
