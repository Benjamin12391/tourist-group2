package org.ui_master_data;

import org.example.Hotelutil;
import org.example.Hotel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    public MainFrame() throws FileNotFoundException {
        super("Main Frame");
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTable table= new JTable();
        table.setDefaultEditor(Object.class, null);
        ArrayList<Hotel> allhotels=new ArrayList<>();




        HotelTableModel model = new HotelTableModel(  Hotelutil.HotelData());

        table.setModel(model);



        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    int row = table.getSelectedRow();
                    if(row>0){
                        Hotel temp= model.getHotel(row);
                        new EditingWindow(MainFrame.this, temp,()-> model.refreshRow(row)).setVisible(true);
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
