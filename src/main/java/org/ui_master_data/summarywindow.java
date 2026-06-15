package org.ui_master_data;

import javax.swing.*;
import java.awt.*;
import javax.swing.JOptionPane;

public class summarywindow extends JFrame {
    public summarywindow() {
        super("Summary Window");
        setSize(500,200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLayout(new GridLayout(6,5));
        setLayout(new BorderLayout());

        String[] header ={"Category","Sum hotels","average beds", "average rooms"};

        String[][] s=summarydata.summarydata();
        JTable table=new JTable(s,header);
        JScrollPane scroll=new JScrollPane(table);

        table.setEnabled(false);
        add(scroll,BorderLayout.CENTER);


        setVisible(true);








    }
}
