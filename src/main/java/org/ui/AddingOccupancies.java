package org.ui;

import org.ui_master_data.HotelTableModel;
import org.ui_transactional_data.HotelTableModel_transaction;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class AddingOccupancies extends JFrame {

    private final int hotelId;
    private final HotelTableModel_transaction tmodel;
    private final HotelTableModel model;

    public AddingOccupancies(HotelTableModel model,int hotelId, HotelTableModel_transaction tmodel) {
        super("Adding Occupancies");
        this.model = model;
        this.hotelId = hotelId;
        this.tmodel = tmodel;
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable htable = new JTable(model);
        TableRowSorter<HotelTableModel> hsorter = new TableRowSorter<>(model);
        hsorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(RowFilter.Entry<? extends HotelTableModel, ? extends Integer> entry) {
                return Integer.valueOf(hotelId).equals(entry.getValue(0));
            }
        });
        htable.setRowSorter(hsorter);

        JTable table = new JTable(tmodel);
        TableRowSorter<HotelTableModel_transaction> sorter = new TableRowSorter<>(tmodel);
        sorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(RowFilter.Entry<? extends HotelTableModel_transaction, ? extends Integer> entry) {
                return Integer.valueOf(hotelId).equals(entry.getValue(0));
            }
        });
        table.setRowSorter(sorter);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(htable),
                new JScrollPane(table)
        );
        splitPane.setResizeWeight(0.25);
        splitPane.setDividerLocation(40);

        add(splitPane, BorderLayout.CENTER);


        JButton addOccupancy = new JButton("Add Occupancy");
        addOccupancy.addActionListener(e -> {
            new org.ui_transactional_data.newOccupanciesWindow(hotelId, tmodel).setVisible(true);
        });

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(addOccupancy);
        add(south, BorderLayout.SOUTH);


    }



}
