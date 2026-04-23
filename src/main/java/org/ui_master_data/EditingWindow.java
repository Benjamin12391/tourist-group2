package org.ui_master_data;

import org.example.Hotel;

import javax.swing.*;
import java.awt.*;

public class EditingWindow extends JDialog {

    public EditingWindow(MainFrame mainFrame, Hotel hotel, Runnable okAction) {
        setSize(500,500);
        setTitle("Edit Window");
        setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea();
        textArea.setText(hotel.toString());
        add(textArea, BorderLayout.CENTER);
        textArea.setEditable(false);
        JButton okbutton = new JButton("Save");
        add(okbutton, BorderLayout.SOUTH);

        okbutton.addActionListener(e -> {
           hotel.setName("Changed by Button");
           okAction.run();

           dispose();

        });


    }


}
