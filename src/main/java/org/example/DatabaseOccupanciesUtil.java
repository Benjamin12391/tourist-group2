package org.example;

import java.util.List;

public class DatabaseOccupanciesUtil {
    private final OccupanciesDAO dao = new OccupanciesDAO();

    public List<occupancies> master_data_occupancies() {
        return dao.findAll();
    }

    public List<occupancies> forHotel(int hotelId) {
        return dao.findByHotelId(hotelId);
    }
}

