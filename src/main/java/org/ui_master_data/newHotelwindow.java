package org.ui_master_data;

import javax.swing.*;
import javax.swing.text.FlowView;
import java.awt.*;

public class newHotelwindow extends JFrame {
    public newHotelwindow() {
        super("New Hotel");
        setSize(1000, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        String[] header= {"category","name","owner","contact","address","city","cityCode","phone","noRooms","noBeds"};
        setLayout(new GridLayout(0,header.length));
        String[] t1= new String[header.length];
        JTextField[] t2= new JTextField[header.length];
        for(String s : header)
        {
            JLabel label = new JLabel(s);
            label.setHorizontalAlignment(JLabel.CENTER);

            add( label);
        }
        for(int i=0;i<header.length;i++){
            JTextField input = new JTextField();
            input.setColumns(1);
            input.setEditable(true);
            t2[i]=input;

            add(input);

        }


        JButton button = new JButton("save");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setVerticalAlignment(JButton.CENTER);
        add(button);

        button.addActionListener(e -> {
            for(int i=0;i<header.length;i++){
                t1[i]=t2[i].getText();
                if(t1[i].isBlank()){
                    JOptionPane.showMessageDialog(this,"Please fill out all fields");
                    return;
                }

            }



            new popup().setVisible(true);

        });




    }



}
