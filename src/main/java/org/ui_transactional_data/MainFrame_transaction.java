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
        table.setAutoCreateRowSorter(true);



        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    int viewRow = table.getSelectedRow();
                    if(viewRow >= 0){
                        int modelRow = table.convertRowIndexToModel(viewRow);
                        occupancies temp= model.getHotel(modelRow);
                        new EditingWindow_transaction(MainFrame_transaction.this, temp,()-> model.refreshRow(modelRow)).setVisible(true);
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
