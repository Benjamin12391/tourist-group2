package org.example;

import java.util.List;

/**
 * Small adapter to retrieve Hotel data from the database via HotelDAO.
 */
public class DatabaseHotelUtil {
    private final HotelDAO dao = new HotelDAO();

    public List<Hotel> HotelData() {
        return dao.findAll();
    }
}

