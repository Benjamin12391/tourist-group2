package org.ui_transactional_data;



import org.example.Hotel;
import org.example.occupancies;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public  class HotelTableModel_transaction extends AbstractTableModel {
    private ArrayList<occupancies> hotels;

    private String[] cols= {"id","rooms","usedrooms","beds","usedbeds","year","month"};
    HotelTableModel_transaction(ArrayList<occupancies> hotels){
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

}
