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
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Hotel h = hotels.get(rowIndex);
        switch(columnIndex) {
            case 1 -> h.setCategory(value.toString());
            case 2 -> h.setName(value.toString());
            case 3 -> h.setOwner(value.toString());
            case 4 -> h.setContact(value.toString());
            case 5 -> h.setAddress(value.toString());
            case 6 -> h.setCity(value.toString());
            case 7 -> h.setCitycode(value.toString());
            case 8 -> h.setPhone(value.toString());
            case 9 -> h.setNoRooms(Integer.parseInt(value.toString()));
            case 10 -> h.setNoBeds(Integer.parseInt(value.toString()));

        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 0||columnIndex == 9||columnIndex == 10){
            return Integer.class;
        }
        return String.class;

    }

    public Hotel getHotel(int in){
        return hotels.get(in);
    }

    public void refreshRow(int row){
        fireTableRowsUpdated(row, row);
    }

}
