package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



//id,rooms,usedrooms,beds,usedbeds,year,month

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //master_data_occupancies();
        String path="src/main/resources/Hoteldata.csv";
        Scanner sc = new Scanner(new File(path));
        ArrayList<Hotel> hotels = new ArrayList<>();
        sc.nextLine();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            line = line.replace("\"", "");

            String parts[] = line.split(",");

            int id = Integer.parseInt(parts[0]);
            String category = parts[1];
            String name = parts[2];
            String owner = parts[3];
            String contact = parts[4];
            String address = parts[5];
            String city = parts[6];
            String cityCode = parts[7];
            String phone = parts[8];
            int noRooms = Integer.parseInt(parts[9]);
            int noBeds = Integer.parseInt(parts[10]);

            Hotel hotel= new Hotel(id,category,name,owner, contact, address, city, cityCode, phone, noRooms, noBeds);
            hotels.add(hotel);

        }
        hotels.forEach(System.out::println);





    }

    private static void master_data_occupancies() throws FileNotFoundException {
        String path="src/main/resources/Hotels.csv";
        Scanner sc = new Scanner(new File(path));
        List<occupancies> hotels=new ArrayList<>();
        while (sc.hasNextLine()) {
            String l=sc.nextLine();
            String[] p=l.split(",");

                int id =Integer.parseInt(p[0]);
                int room =Integer.parseInt(p[1]);
                int usedrooms =Integer.parseInt(p[2]);
                int beds =Integer.parseInt(p[3]);
                int usedbed =Integer.parseInt(p[4]);
                int year=Integer.parseInt(p[5]);
                int month=Integer.parseInt(p[6]);
                occupancies h= new occupancies(id,room,usedrooms,beds,usedbed,year,month );
                hotels.add(h);
        }
        for (occupancies h : hotels){
            System.out.println(h);
        }
    }
}