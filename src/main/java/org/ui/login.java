package org.ui;

import javax.swing.*;
import java.awt.*;

public class login extends JFrame {
    static int ATP=0;
    public login() {
        setTitle("Login");
        setSize(300,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        JPanel panel = new JPanel(new GridLayout(2,2,10,10));
        JButton button = new JButton("Login");
        button.setEnabled(false);



        JTextField user= new JTextField();
        JPasswordField pass= new JPasswordField();

        panel.add(new JLabel("Username:"));

        panel.add(user);


        panel.add(new JLabel("Password:"));

        panel.add(pass);
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(panel,BorderLayout.CENTER);
        add(button,BorderLayout.SOUTH);
        button.addActionListener(e -> {
            if(user.getText().isBlank()){
                JOptionPane.showMessageDialog(this,"Please enter username");
                return;
            }
                   // JOptionPane.showMessageDialog(null,"Welcome to Login "+user.getText());
            if(user.getText().equals("Junior")&&pass.getText().equals("Junior")){
                new Junior().setVisible(true);
                dispose();
            }
            if(user.getText().equals("Senior")&&pass.getText().equals("Senior")){
                new Senior().setVisible(true);
                dispose();

            }
            else if(user.getText().equals("admin")&&pass.getText().equals("admin")){
                new Admin().setVisible(true);
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(this,"Invalid username or password");
            }


                });
        user.addCaretListener(e -> {
            System.out.println("something was entered");
        });
        /*user.addCaretListener(e -> {
            if(user.getText().length()>=5){
                button.setEnabled(true);
            }
            else if(user.getText().length()<5){
                button.setEnabled(false);
            }
        });*/
        pass.addCaretListener(e -> {
            if((pass.getText().length()>=5)&&user.getText().length()>=5){
                button.setEnabled(true);
            }
            else if((pass.getText().length()<5)||user.getText().length()<5){
                button.setEnabled(false);
            }

        });


        user.addCaretListener(e -> {
            if((pass.getText().length()>=5)&&user.getText().length()>=5){
                button.setEnabled(true);
            }
            else if((pass.getText().length()<5)||user.getText().length()<5){
                button.setEnabled(false);
            }
        });
        button.addActionListener(e -> {
            ATP++;
            if(ATP>3){
                JOptionPane.showMessageDialog(this,"to many attempts");
                System.exit(0);
                return;
            }
        });







        





    }




}
