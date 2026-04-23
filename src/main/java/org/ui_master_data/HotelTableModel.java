package org.ui_master_data;



import org.example.Hotel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public  class HotelTableModel extends AbstractTableModel {
    private ArrayList<Hotel> hotels;

    private String[] cols= {"id","category","name","owner","contact","address","city","cityCode","phone","noRooms","noBeds"};
    HotelTableModel(ArrayList<Hotel> hotels){
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
        Hotel h = hotels.get(rowIndex);

        return switch(columnIndex){
            case 0 -> h.getId();
            case 1 -> h.getCategory();
            case 2 -> h.getName();
            case 3 -> h.getOwner();
            case 4 -> h.getContact();
            case 5 -> h.getAddress();
            case 6 -> h.getCity();
            case 7 -> h.getCitycode();
            case 8 -> h.getPhone();
            case 9 -> h.getNoRooms();
            case 10 -> h.getNoBeds();
            default -> null;
        };
    }
    public Hotel getHotel(int in){
        return hotels.get(in);
    }

    public void refreshRow(int row){
        fireTableRowsUpdated(row, row);
    }

}
