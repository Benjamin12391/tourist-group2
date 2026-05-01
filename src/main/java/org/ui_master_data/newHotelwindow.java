package org.ui_master_data;

import org.example.Hotel;
import org.example.Hotelutil;
import org.ui_transactional_data.HotelTableModel_transaction;
import org.ui_transactional_data.MainFrame_transaction;
import org.ui_transactional_data.newOccupanciesWindow;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class newHotelwindow extends JFrame {
    public newHotelwindow(HotelTableModel model) {
        super("New Hotel");
        setSize(1000, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        String[] header= {"category","name","owner","contact","address","city","cityCode","phone","noRooms","noBeds"};
        JPanel panel = new JPanel(new GridLayout(2, header.length));
        //setLayout(new GridLayout(0,header.length));
        String[] t1= new String[header.length];
        JTextField[] t2= new JTextField[header.length];
        for(String s : header)
        {
            JLabel label = new JLabel(s);
            label.setHorizontalAlignment(JLabel.CENTER);

            panel.add( label);
        }
        for(int i=0;i<header.length;i++){
            JTextField input = new JTextField();
            input.setColumns(1);
            input.setEditable(true);
            t2[i]=input;

            panel.add(input);

        }

        this.add(panel, BorderLayout.CENTER);
        JButton button = new JButton("save");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setVerticalAlignment(JButton.CENTER);
        this.add(button, BorderLayout.SOUTH);

        button.addActionListener(e -> {
            for(int i=0;i<header.length;i++){
                t1[i]=t2[i].getText();
                if(t1[i].isBlank()){
                    JOptionPane.showMessageDialog(this,"Please fill out all fields");
                    return;
                }

            }


            // record is ok
            int newId=model.maxId()+1;
            Hotel hotel = new Hotel(
                    newId,
                    t1[0],
                    t1[1],
                    t1[2],
                    t1[3],
                    t1[4],
                    t1[5],
                    t1[6],
                    t1[7],
                    Integer.parseInt(t1[8]),
                    Integer.parseInt(t1[9])
            );
            model.addHotel(hotel);
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Would you like to add occupancies?",
                    "Create occupancies",
                    JOptionPane.YES_NO_OPTION
            );
            if(response == JOptionPane.YES_OPTION){
                try {
                    MainFrame_transaction mftrans= new MainFrame_transaction();
                    mftrans.setVisible(true);
                    HotelTableModel_transaction trmodel= mftrans.getModel();
                    //new MainFrame_transaction().setVisible(true);
                   new newOccupanciesWindow(newId, trmodel).setVisible(true);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }


            }
            else {
                new popup().setVisible(true);
            }





        });




    }



}
