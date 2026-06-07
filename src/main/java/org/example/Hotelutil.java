package org.example;

import java.util.ArrayList;

public class Hotelutil {
    public static ArrayList<Hotel> HotelData() {
        // Use the database as single source of truth. If the DB call fails we throw an exception
        try {
            DatabaseHotelUtil db = new DatabaseHotelUtil();
            java.util.List<Hotel> list = db.HotelData();
            if (list != null) {
                return new ArrayList<>(list);
            }
            throw new RuntimeException("No data returned from database");
        } catch (RuntimeException re) {
            throw re;
        } catch (Throwable t) {
            throw new RuntimeException("Failed to load hotels from database: " + t.getMessage(), t);
        }
    }
}
