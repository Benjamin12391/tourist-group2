package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hotelutil {
    public static ArrayList<Hotel> HotelData() throws FileNotFoundException {
        String path="src/main/resources/Hotel.csv";
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
        //hotels.forEach(System.out::println);
        return  hotels;
    }
}
