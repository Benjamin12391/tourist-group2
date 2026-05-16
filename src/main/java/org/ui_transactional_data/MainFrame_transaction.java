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
        private HotelTableModel_transaction model_transaction;
    public MainFrame_transaction() throws FileNotFoundException {
        super("Main Frame");
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTable ttable= new JTable();
        //ttable.setDefaultEditor(Object.class, null);
        ArrayList<Hotel> allhotels=Hotelutil.HotelData();




        this.model_transaction = new HotelTableModel_transaction((ArrayList<occupancies>) occupanciesutil.master_data_occupancies());

        ttable.setModel(model_transaction);
        ttable.setAutoCreateRowSorter(true);
        this.model_transaction = new HotelTableModel_transaction((ArrayList<occupancies>) occupanciesutil.master_data_occupancies());
        ttable.setModel(model_transaction);
        ttable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    int viewRow = ttable.getSelectedRow();
                    if(viewRow >= 0){
                        int modelRow = ttable.convertRowIndexToModel(viewRow);
                        occupancies temp= model_transaction.getHotel(modelRow);
                        new EditingWindow_transaction(MainFrame_transaction.this, temp,()-> model_transaction.refreshRow(modelRow)).setVisible(true);
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
        add(new JScrollPane(ttable));


















    }

    public HotelTableModel_transaction getModel() {
        return this.model_transaction;
    }




}
