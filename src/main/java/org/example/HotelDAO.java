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
            Hotel managed = (Hotel) session.merge(hotel);
            tx.commit();
            return managed;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
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

