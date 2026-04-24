package org.ui_transactional_data;

import org.example.occupancies;

import javax.swing.*;
import java.awt.*;

public class EditingWindow_transaction extends JDialog {

    public EditingWindow_transaction(MainFrame_transaction mainFrame, occupancies hotel, Runnable okAction) {
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
           //occupancies.setName("Changed by Button");
           okAction.run();

           dispose();

        });


    }


}
