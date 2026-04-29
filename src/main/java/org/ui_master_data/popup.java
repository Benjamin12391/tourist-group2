package org.ui_master_data;

import javax.swing.*;
import java.awt.*;


public class popup extends javax.swing.JFrame {
    public popup() {
        super("Hotel saved");
        setSize(300,300);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
        JLabel label = new JLabel("Hotel has been saved");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
        JButton button = new JButton("OK");
        button.setPreferredSize(new Dimension(80, 30));


        button.setBorder(BorderFactory.createLineBorder(Color.black));

        button.addActionListener(e->{
            dispose();
        });

       add(button);


    }
}
