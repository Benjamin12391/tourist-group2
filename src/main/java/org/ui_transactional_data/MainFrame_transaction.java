package org.ui_transactional_data;

import org.example.Hotel;
import org.example.Hotelutil;
import org.example.occupancies;
import org.example.occupanciesutil;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainFrame_transaction extends JFrame {

    public MainFrame_transaction() throws FileNotFoundException {
        super("Main Frame");
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTable table= new JTable();
        table.setDefaultEditor(Object.class, null);
        ArrayList<Hotel> allhotels=new ArrayList<>();




        HotelTableModel_transaction model = new HotelTableModel_transaction((ArrayList<occupancies>) occupanciesutil.master_data_occupancies());

        table.setModel(model);
        /*
       // id,category,name,owner,contact,address,city,cityCode,phone,noRooms,noBeds
        model.addColumn("Name");
        model.addColumn("Age");
        model.addColumn("Gender");
        for(int j=0;j<10;j++){
            model.addRow(new String[]{"Hans","Age","Male"});
        }*/


        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){/*
                    int in= table.getSelectedRow();
                    String name = table.getValueAt(in,0).toString();
                    String age = table.getValueAt(in,1).toString();
                    String gender = table.getValueAt(in,2).toString();

                    System.out.println(name+" "+age+" "+gender);
                    //JOptionPane.showMessageDialog(MainFrame.this, "mouse Clicked");
                    new EditingWindow(name,age,gender).setVisible(true);*/
                    int row = table.getSelectedRow();
                    if(row>0){
                        occupancies temp= model.getHotel(row);
                        new EditingWindow_transaction(MainFrame_transaction.this, temp,()-> model.refreshRow(row)).setVisible(true);
                    }

                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        add(new JScrollPane(table));





    }


}
