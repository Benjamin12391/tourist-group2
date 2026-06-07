package org.diagnostic;

import org.example.Hotel;
import org.example.HotelDAO;
import org.example.OccupanciesDAO;
import org.example.occupancies;

import java.io.File;
import java.util.Scanner;

/**
 * Small seeder to import CSV data into the configured database.
 * Run this from your IDE or via command line once to populate the DB.
 */
public class DatabaseSeeder {

    public static void main(String[] args) {
        try {
            seedHotels();
            seedOccupancies();
            System.out.println("Seeding completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void seedHotels() throws Exception {
        String path = "src/main/resources/Hotel.csv";
        Scanner sc = new Scanner(new File(path));
        HotelDAO dao = new HotelDAO();
        if (sc.hasNextLine()) sc.nextLine();
        while (sc.hasNextLine()) {
            String line = sc.nextLine().replace("\"", "");
            String[] parts = line.split(",");
            try {
                int id = Integer.parseInt(parts[0]);
                String category = parts[1];
                String name = parts[2];
                String owner = parts[3];
                String contact = parts[4];
                String address = parts[5];
                String city = parts[6];
                String cityCode = parts[7];
                String phone = parts[8];
                int noRooms = Integer.parseInt(parts[parts.length - 2]);
                int noBeds = Integer.parseInt(parts[parts.length - 1]);

                Hotel h = new Hotel(id, category, name, owner, contact, address, city, cityCode, phone, noRooms, noBeds);
                dao.saveOrUpdate(h);
            } catch (Exception ex) {
                System.err.println("Skipping hotel line due to parse error: " + line);
            }
        }
    }

    private static void seedOccupancies() throws Exception {
        String path = "src/main/resources/Hoteldata.csv";
        Scanner sc = new Scanner(new File(path));
        OccupanciesDAO dao = new OccupanciesDAO();
        while (sc.hasNextLine()) {
            String l = sc.nextLine();
            String[] p = l.split(",");
            try {
                int id = Integer.parseInt(p[0]);
                int room = Integer.parseInt(p[1]);
                int usedrooms = Integer.parseInt(p[2]);
                int beds = Integer.parseInt(p[3]);
                int usedbed = Integer.parseInt(p[4]);
                int year = Integer.parseInt(p[5]);
                int month = Integer.parseInt(p[6]);
                occupancies o = new occupancies(id, room, usedrooms, beds, usedbed, year, month);
                dao.saveOrUpdate(o);
            } catch (Exception ex) {
                System.err.println("Skipping occupancy line due to parse error: " + l);
            }
        }
    }
}

