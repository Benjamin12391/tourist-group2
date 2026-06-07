package org.ui_master_data;

import org.example.Hotel;
import org.example.Hotelutil;
import org.ui_transactional_data.HotelTableModel_transaction;
import org.ui_transactional_data.MainFrame_transaction;
import org.ui_transactional_data.newOccupanciesWindow;

import javax.swing.*;
import java.awt.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class newHotelwindow extends JFrame {
    public newHotelwindow(HotelTableModel model) {
        super("New Hotel");
        setSize(1000, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        String[] header= {"category","name","owner","contact","address","city","cityCode","phone","noRooms","noBeds","options"};
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
        // add a small options chooser and save button in the south
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton chooseOptionsBtn = new JButton("choose options");
        JButton button = new JButton("save");
        southPanel.add(chooseOptionsBtn);
        southPanel.add(button);
        this.add(southPanel, BorderLayout.SOUTH);

        // predefined options - user can choose one or multiple
        String[] predefinedOptions = {"WiFi", "Breakfast", "Parking", "Pool", "Gym"};
        chooseOptionsBtn.addActionListener(ev -> {
            JPanel opts = new JPanel(new GridLayout(predefinedOptions.length,1));
            JCheckBox[] boxes = new JCheckBox[predefinedOptions.length];
            for(int i=0;i<predefinedOptions.length;i++){
                boxes[i] = new JCheckBox(predefinedOptions[i]);
                opts.add(boxes[i]);
            }
            int res = JOptionPane.showConfirmDialog(this, opts, "Select options", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(res == JOptionPane.OK_OPTION){
                StringBuilder sb = new StringBuilder();
                for(int i=0;i<boxes.length;i++){
                    if(boxes[i].isSelected()){
                        if(sb.length()>0) sb.append(",");
                        sb.append(predefinedOptions[i]);
                    }
                }
                // set the last text input (options) to the selected comma-separated values
                t2[header.length-1].setText(sb.toString());
            }
        });

        button.addActionListener(e -> {
            for(int i=0;i<header.length;i++){
                t1[i]=t2[i].getText();
                // last field 'options' is optional
                if(t1[i].isBlank() && i != header.length-1){
                    JOptionPane.showMessageDialog(this,"Please fill out all fields");
                    return;
                }
                if(t1[i].isBlank()){
                    t1[i] = "";
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
            // set optional options field
            hotel.setOptions(t1[10]);
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
                        new newOccupanciesWindow(newId, trmodel).setVisible(true);
                    } catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(null, "Failed to open transactional frame: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        throw ex;
                    }


            }
            else {
                new popup().setVisible(true);
            }





        });




    }



}
