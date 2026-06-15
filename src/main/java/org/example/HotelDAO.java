package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HotelDAO {

    public List<Hotel> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Hotel", Hotel.class).list();
        }
    }

    public Hotel findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Hotel.class, id);
        }
    }

    public Hotel saveOrUpdate(Hotel hotel) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            // Use persist for new entities (id == null) and merge for existing ones
            if (hotel.getId() == null) {
                // The DB table may not use IDENTITY for id. Compute a new id safely here
                // by selecting the current max(id) and incrementing. This is not ideal
                // for high-concurrency environments but works for small desktop apps.
                Integer maxId = session.createQuery("select max(h.id) from Hotel h", Integer.class).uniqueResult();
                int nextId = (maxId == null) ? 1 : (maxId + 1);
                hotel.setId(nextId);
                session.persist(hotel);
            } else {
                // For existing entities, merge to update them in the database
                hotel = (Hotel) session.merge(hotel);
            }
            tx.commit();
            // Refresh to get any generated values from DB
            session.refresh(hotel);
            return hotel;
        } catch (Exception e) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception rollbackEx) {
                    // Connection might already be closed
                }
            }
            throw e;
        }
    }

    public void deleteById(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Hotel h = session.get(Hotel.class, id);
            if (h != null) {
                session.remove(h);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

}

