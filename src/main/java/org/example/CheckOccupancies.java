package org.example;

import java.util.List;

/**
 * Small helper to quickly print how many occupancies are visible via the DAO and show a few rows.
 * Run after 'mvn package' with the project's classpath.
 */
public class CheckOccupancies {
    public static void main(String[] args) {
        try {
            OccupanciesDAO dao = new OccupanciesDAO();
            List<occupancies> all = dao.findAll();
            System.out.println("Found occupancies: " + (all == null ? 0 : all.size()));
            if (all != null) {
                for (int i = 0; i < Math.min(10, all.size()); i++) {
                    occupancies o = all.get(i);
                    System.out.println(String.format("pk=%s hotelId=%d rooms=%d used=%d beds=%d usedbeds=%d year=%d month=%d",
                            o.getPk(), o.getId(), o.getRoom(), o.getUsedrooms(), o.getBeds(), o.getUsedbeds(), o.getYear(), o.getMonth()));
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching occupancies: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

