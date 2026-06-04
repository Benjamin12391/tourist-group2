package org.ui;

import org.ui_master_data.HotelTableModel;
import org.ui_transactional_data.HotelTableModel_transaction;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class AddingOccupancies2 extends javax.swing.JFrame {
    private final int hotelId;
    private final HotelTableModel_transaction tmodel;
    private final HotelTableModel model;
    public AddingOccupancies2(HotelTableModel model, int hotelId, HotelTableModel_transaction tmodel) {

        super("Adding Occupancies");
        setSize(600, 400);
        this.hotelId = hotelId;
        this.tmodel = tmodel;
        this.model = model;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JTable htable = new JTable(model);
        htable.setDefaultEditor(Object.class, null);
        TableRowSorter<HotelTableModel> hsorter = new TableRowSorter<>(model);
        hsorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(RowFilter.Entry<? extends HotelTableModel, ? extends Integer> entry) {
                return Integer.valueOf(hotelId).equals(entry.getValue(0));
            }
        });
        htable.setRowSorter(hsorter);



        JTable table = new JTable(tmodel);
        table.setDefaultEditor(Object.class, null);

        TableRowSorter<HotelTableModel_transaction> sorter = new TableRowSorter<>(tmodel);
        sorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(RowFilter.Entry<? extends HotelTableModel_transaction, ? extends Integer> entry) {
                return Integer.valueOf(hotelId).equals(entry.getValue(0));
            }
        });
        table.setRowSorter(sorter);
        // Edit a sinlge cell
        /*table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
*/
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(htable),
                new JScrollPane(table)
        );
        splitPane.setResizeWeight(0.25);
        splitPane.setDividerLocation(40);

        add(splitPane, BorderLayout.CENTER);



    }


}
