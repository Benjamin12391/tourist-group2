package org.ui;

import org.example.Hotel;
import org.example.Hotelutil;
import org.example.occupancies;
import org.example.occupanciesutil;
import org.ui_master_data.HotelTableModel;


import org.ui_master_data.newHotelwindow;
import org.ui_master_data.summarywindow;
import org.ui_transactional_data.HotelTableModel_transaction;
import org.ui_transactional_data.MainFrame_transaction;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Senior2 extends JFrame {
    private final TableRowSorter<HotelTableModel> sorter;
    private  HotelTableModel_transaction model_transaction;
    public HotelTableModel_transaction getModel() {
        return this.model_transaction;
    }

    public Senior2() {
        super("Lower Austria Tourist Agency");
        setSize(600,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        try {
            this.model_transaction = new HotelTableModel_transaction((ArrayList<occupancies>) occupanciesutil.master_data_occupancies());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JTable ttable= new JTable();
        ttable.setModel(model_transaction);
        ttable.setAutoCreateRowSorter(true);

        JTable htable= new JTable();

        ArrayList<Hotel> allhotels=new ArrayList<>();


        final HotelTableModel model;
        try {
            model = new HotelTableModel(Hotelutil.HotelData());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        sorter = new TableRowSorter<>(model);
        htable.setRowSorter(sorter);

        htable.setModel(model);
        // Edit a sinlge cell
        htable.setRowSelectionAllowed(false);
        htable.setColumnSelectionAllowed(false);
        htable.setCellSelectionEnabled(true);
        htable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        htable.setAutoCreateRowSorter(true);
        int colcount=htable.getColumnCount();
        String[] header=new String[colcount];
        for(int i=0;i<colcount;i++) {
            header[i] = htable.getColumnName(i);
        }
        JPanel north=new JPanel();
        north.setLayout(new GridLayout(1,5));
        add(new JScrollPane(htable), BorderLayout.CENTER);
        JLabel jlfilter=new JLabel("Filter by:");
        north.add(jlfilter);
        JTextField jtfname=new JTextField();
        jtfname.setColumns(20);
        north.add(jtfname,BorderLayout.NORTH);
        JButton execute=new JButton("execute");
        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RowFilter<HotelTableModel, Object> filter =null;
                String txt=jtfname.getText();
                if (!txt.isBlank()) {
                    if(txt.contains("*")){
                        String tneu="";
                        for(int i=0;i<txt.length();i++){
                            if(txt.charAt(i)=='*'){
                                tneu+="\\";
                            }
                            tneu+=txt.charAt(i);
                        }

                        txt=tneu+"$";
                    }
                    filter = RowFilter.regexFilter("^" + txt);

                }

                sorter.setRowFilter(filter);
                htable.setRowSorter(sorter);
            }

        });
        north.add(execute);

        JButton summary = new JButton("Summary");
        north.add(summary);
        summary.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    new summarywindow().setVisible(true);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
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
        JButton newHotel = new JButton("New Hotel");
        north.add(newHotel);
        newHotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new newHotelwindow(model).setVisible(true);
            }
        });
        htable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){

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


        JButton loadOccupancies = new JButton("Load Occupancies");
        north.add(loadOccupancies);
        loadOccupancies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int viewRow = htable.getSelectedRow();
                if(viewRow != -1){
                    int modelRow = htable.convertRowIndexToModel(viewRow);
                    int hotelId = model.getHotel(modelRow).getId();
                    new AddingOccupancies(model, hotelId, model_transaction).setVisible(true);
                }

            }
        });
        add(north,BorderLayout.NORTH);
        //--- West
        JPanel west=new JPanel();
        int anz_buttons =7;
        GridLayout grid=new GridLayout(anz_buttons,1);
        grid.setHgap(25);
        west.setLayout(grid);
        JButton deleteHotel  = new JButton("Delete Hotel");
        west.add(deleteHotel);
        deleteHotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int viewRow = htable.getSelectedRow();
                if(viewRow != -1){
                    int modelRow = htable.convertRowIndexToModel(viewRow);
                    int hotelId = model.getHotel(modelRow).getId();


                    //-Transactional Data

                    JTable table = new JTable(model_transaction);
                    TableRowSorter<HotelTableModel_transaction> sorter = new TableRowSorter<>(model_transaction);
                    sorter.setRowFilter(new RowFilter<>() {
                        @Override
                        public boolean include(RowFilter.Entry<? extends HotelTableModel_transaction, ? extends Integer> entry) {
                            return Integer.valueOf(hotelId).equals(entry.getValue(0));
                        }
                    });
                    table.setRowSorter(sorter);
                    // - Security call
                    int yn=JOptionPane.showConfirmDialog(null,"are you sure","Delete conformation",JOptionPane.YES_NO_OPTION);
                    if(yn==JOptionPane.YES_OPTION) {
                        // - Delete all Transactional Data
                        int[] rowsToDelete = new int[table.getRowCount()];
                        for (int i = 0; i < table.getRowCount(); i++) {
                            rowsToDelete[i] = table.convertRowIndexToModel(i);
                        }
                        model_transaction.removeRows(rowsToDelete);
                        // - Delete the hotel too
                        model.removeHotelById(hotelId);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Hotel was not deleted","Delete conformation",JOptionPane.INFORMATION_MESSAGE);
                    }
                }


            }
        });
        west.add(new JLabel(""));

        west.add(new JLabel(""));
        west.add(new JLabel(""));

        west.add(new JLabel(""));
        west.add(new JLabel(""));
        west.add(new JLabel(""));
        add(west,BorderLayout.WEST);









    }


}

