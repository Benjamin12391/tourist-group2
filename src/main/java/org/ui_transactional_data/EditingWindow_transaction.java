package org.ui_transactional_data;

import org.example.occupancies;

import javax.swing.*;
import java.awt.*;

public class EditingWindow_transaction extends JDialog {

    public EditingWindow_transaction(MainFrame_transaction mainFrame, occupancies hotel, Runnable okAction) {
        setSize(1000,200);
        setTitle("Edit Window");
        setLocationRelativeTo(null);
        String[] s= {"id","room","usedrooms","beds","usedbed","year","month"};
        JPanel panel = new JPanel(new GridLayout(2, s.length));
        setLayout(new GridLayout(2,s.length));
        for(int i=0;i<s.length;i++){
            JLabel l=new JLabel(s[i]);
            l.setHorizontalAlignment(JLabel.CENTER);
            add(l);
        }
        String[] v={
                String.valueOf(hotel.getId()),
                String.valueOf(hotel.getRoom()),
                String.valueOf(hotel.getUsedrooms()),
                String.valueOf(hotel.getBeds()),
                String.valueOf(hotel.getUsedbeds()),
                String.valueOf(hotel.getYear()),
                String.valueOf(hotel.getMonth())


        };
        this.add(panel, BorderLayout.CENTER);



       for(String vl: v){
           JTextField t=new JTextField(vl);
           t.setColumns(1);
           t.setEditable(true);

           add(t);
       }
       JButton button=new JButton("save");
       button.addActionListener(e->{
           JOptionPane.showMessageDialog(this,"Changes have been saved");
       });
        this.add(button, BorderLayout.SOUTH);
        /*JButton okbutton = new JButton("Save");
        add(okbutton, BorderLayout.SOUTH);

        okbutton.addActionListener(e -> {
           //occupancies.setName("Changed by Button");
           okAction.run();

           dispose();

        });

         */


    }


}
