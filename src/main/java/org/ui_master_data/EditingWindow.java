package org.ui_master_data;

import lombok.SneakyThrows;
import org.example.Hotel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class EditingWindow extends JDialog {

    public EditingWindow(MainFrame mainFrame, Hotel hotel, Runnable okAction, int colcount, String[] header) {
        setSize(500,500);
        setTitle("Edit Window");
        setLocationRelativeTo(null);
        String original=hotel.toString();
        String edited=original.toString();
        JTextArea textArea = new JTextArea();
        textArea.setText(edited);
        add(textArea, BorderLayout.CENTER);
        textArea.setEditable(true);
        JButton okbutton = new JButton("Save");
        add(okbutton, BorderLayout.SOUTH);



        final String[] temp = {""};
        final int[] starts = {0};
        final boolean[] eingegeben   = {false};
        textArea.getDocument().addDocumentListener(new DocumentListener() {

            @SneakyThrows
            @Override
            public void insertUpdate(DocumentEvent e) {
                int start = e.getOffset();
                int laenge =  e.getLength();
                temp[0] =temp[0] +e.getDocument().getText(start,laenge);
                if( !eingegeben[0]){
                    starts[0] = start;
                    eingegeben[0] = true;
                }
               // System.out.println(temp[0]);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {}


        });

        okbutton.addActionListener(e -> {
           //hotel.setName(temp[0]);

            okAction.run();
            int anzahlcol=colcount;
            String teil =original.substring(0,starts[0]-1);
            int last =teil.lastIndexOf("=");
            teil=teil.substring(last+1);
            boolean found = false;
            int last_found=0;
            for(int i=0;i<anzahlcol;i++){
                found=teil.contains(header[i]);
                if(found){
                    last_found=i;
                }
            }
            switch(last_found){
                case 1 -> hotel.setCategory(temp[0]);
                case 2 -> hotel.setName(temp[0]);
                case 3 -> hotel.setOwner(temp[0]);
                case 4 -> hotel.setContact(temp[0]);
                case 5 -> hotel.setAddress(temp[0]);
                case 6 -> hotel.setCity(temp[0]);
                case 7 -> hotel.setCitycode(temp[0]);
                case 8 -> hotel.setPhone(temp[0]);
                case 9 -> hotel.setNoRooms(Integer.parseInt(temp[0]));
                case 10 -> hotel.setNoBeds(Integer.parseInt(temp[0]));
            }


            JOptionPane.showMessageDialog(EditingWindow.this, "Your entry was saved");

           dispose();

        });


    }


}
