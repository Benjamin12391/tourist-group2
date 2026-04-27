package org.ui_master_data;

import org.example.Hotelutil;
import org.example.Hotel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainFrame extends JFrame {
    private final TableRowSorter<HotelTableModel> sorter;
    public MainFrame() throws FileNotFoundException {
        super("Main Frame");
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTable table= new JTable();

        ArrayList<Hotel> allhotels=new ArrayList<>();


        HotelTableModel model = new HotelTableModel(Hotelutil.HotelData());
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        table.setModel(model);
        // Edit a sinlge cell
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.setAutoCreateRowSorter(true);
        int colcount=table.getColumnCount();
        String[] header=new String[colcount];
        for(int i=0;i<colcount;i++) {
            header[i] = table.getColumnName(i);
        }
        JPanel north=new JPanel();
        north.setLayout(new GridLayout(1,5));
        add(new JScrollPane(table), BorderLayout.CENTER);
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
                table.setRowSorter(sorter);
            }

        });
        north.add(execute);
        add(north,BorderLayout.NORTH);

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







    }



}
