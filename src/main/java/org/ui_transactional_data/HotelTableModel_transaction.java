package org.ui_transactional_data;


import lombok.Getter;
import org.example.occupancies;
import org.example.OccupanciesDAO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;


public  class HotelTableModel_transaction extends AbstractTableModel {
    private ArrayList<occupancies> hotels;
    private HotelTableModel_transaction model;
    private boolean editable = true;
    private final OccupanciesDAO dao = new OccupanciesDAO();

    private String[] cols= {"id","rooms","usedrooms","beds","usedbeds","year","month"};
    public HotelTableModel_transaction(ArrayList<occupancies> hotels){
        this.hotels = hotels;

    }

    public HotelTableModel_transaction(ArrayList<occupancies> hotels, boolean editable){
        this.hotels = hotels;
        this.editable = editable;

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
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        occupancies h = hotels.get(rowIndex);
        int intValue = Integer.parseInt(value.toString());

        switch (columnIndex) {
            case 1 -> h.setRoom(intValue);
            case 2 -> h.setUsedrooms(intValue);
            case 3 -> h.setBeds(intValue);
            case 4 -> h.setUsedbeds(intValue);
            case 5 -> h.setYear(intValue);
            case 6 -> h.setMonth(intValue);
            default -> {
                return;
            }
        }

        fireTableCellUpdated(rowIndex, columnIndex);
        // persist change
        dao.saveOrUpdate(h);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable && columnIndex != 0;
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
        // persist to DB and obtain managed instance (with pk)
        occupancies managed = dao.saveOrUpdate(oc);
        int row = hotels.size();
        hotels.add(managed != null ? managed : oc);
        fireTableRowsInserted(row, row);
    }

    public void removeRows(int[] rows) {
        Arrays.sort(rows);
        for (int i = rows.length - 1; i >= 0; i--) {
            occupancies toRemove = hotels.get(rows[i]);
            // delete from DB
            try {
                dao.delete(toRemove);
            } catch (Exception ex) {
                // if delete fails, still remove locally but log
                System.err.println("Failed to delete occupancy: " + ex.getMessage());
            }
            hotels.remove(rows[i]);
        }
        fireTableDataChanged();
    }




}
