package org.example;

import java.util.List;

public class occupanciesutil {
    public static List<occupancies> master_data_occupancies() {
        try {
            DatabaseOccupanciesUtil db = new DatabaseOccupanciesUtil();
            List<occupancies> list = db.master_data_occupancies();
            if (list != null) return list;
            throw new RuntimeException("No occupancies returned from database");
        } catch (RuntimeException re) {
            throw re;
        } catch (Throwable t) {
            throw new RuntimeException("Failed to load occupancies from database: " + t.getMessage(), t);
        }
    }
}
