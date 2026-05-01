package org.ui_transactional_data;

import javax.swing.*;
import java.awt.*;

public class newOccupanciesWindow extends JFrame {
    public newOccupanciesWindow(int id, HotelTableModel_transaction trmodel) {

        super("New Occupancies");

        setSize(1000, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        String[] s={"room","usedrooms","beds","usedbed","year","month"};
        String[] t1= new String[s.length];
        JTextField[] t2= new JTextField[s.length];
        JPanel panel = new JPanel(new GridLayout(2,s.length));
        for(int i=0;i<s.length;i++){
            JLabel label = new JLabel(s[i]);
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label);
        }
        for(int i=0;i<s.length;i++){
            JTextField field = new JTextField();
            field.setColumns(1);
            field.setEditable(true);
            t2[i]=field;
            panel.add(field);
        }
        JButton button = new JButton("Save occupancies");
        add(panel,BorderLayout.CENTER);
        this.add(button,BorderLayout.SOUTH);
        button.addActionListener(e ->{
            for(int i=0;i<t1.length;i++){
                t1[i]=t2[i].getText();
                if(t1[i].isEmpty()){
                    JOptionPane.showMessageDialog(this,"Please fill out all fields");
                    return;
                }

            }

            org.example.occupancies newOc = new org.example.occupancies(
                    id,                                 // Die ID vom neuen Hotel[cite: 16]
                    Integer.parseInt(t1[0]),            // rooms
                    Integer.parseInt(t1[1]),            // usedrooms
                    Integer.parseInt(t1[2]),            // beds
                    Integer.parseInt(t1[3]),            // usedbed
                    Integer.parseInt(t1[4]),            // year
                    Integer.parseInt(t1[5])             // month
            );
            trmodel.addOccupancies(newOc);
            JOptionPane.showMessageDialog(this,"New Occupancies added successfully");
            this.dispose();
        });





    }
}
