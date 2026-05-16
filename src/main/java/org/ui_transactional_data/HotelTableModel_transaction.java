package org.ui_transactional_data;


import lombok.Getter;
import org.example.occupancies;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;


public  class HotelTableModel_transaction extends AbstractTableModel {
    private ArrayList<occupancies> hotels;
    private HotelTableModel_transaction model;

    private String[] cols= {"id","rooms","usedrooms","beds","usedbeds","year","month"};
    public HotelTableModel_transaction(ArrayList<occupancies> hotels){
        this.hotels = hotels;

    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public int getRowCount() {
        return hotels.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        occupancies h = hotels.get(rowIndex);

        return switch(columnIndex){
            case 0 -> h.getId();
            case 1 -> h.getRoom();
            case  2 -> h.getUsedrooms();
            case 3 -> h.getBeds();
            case 4 -> h.getUsedbeds();
            case 5 -> h.getYear();
            case 6 -> h.getMonth();
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Integer.class;
    }

    public occupancies getHotel(int in){
        return hotels.get(in);
    }

    public void refreshRow(int row){
        fireTableRowsUpdated(row, row);
    }


    public void addOccupancies(occupancies oc) {
        int row = hotels.size();
        hotels.add(oc);
        fireTableRowsInserted(row, row);
    }

    public void removeRows(int[] rows) {
        Arrays.sort(rows);
        for (int i = rows.length - 1; i >= 0; i--) {
            hotels.remove(rows[i]);
        }
        fireTableDataChanged();
    }




}
