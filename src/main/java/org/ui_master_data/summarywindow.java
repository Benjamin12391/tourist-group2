package org.ui_master_data;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class summarywindow extends JFrame {
    public summarywindow() throws FileNotFoundException {
        super("Summary Window");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6,4));
        String[] header ={"Category","Sum hotels","average beds", "average rooms"};
        for(String s : header){
            JLabel label = new JLabel(s);
            label.setHorizontalAlignment(JLabel.CENTER);
            add( label);
            }
        String[][] s= summarydata.summarydata();
        for(int i=0;i<s.length;i++){
            for(int j=0;j<s[i].length;j++){
                JTextField t=new JTextField(s[i][j]);

                t.setEditable(false);
                add(t);
            }

        }

        setVisible(true);








    }
}
