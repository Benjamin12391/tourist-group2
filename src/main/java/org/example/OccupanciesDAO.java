package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OccupanciesDAO {

    public List<occupancies> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from occupancies", occupancies.class).list();
        }
    }

    public List<occupancies> findByHotelId(int hotelId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from occupancies where id = :hid", occupancies.class)
                    .setParameter("hid", hotelId)
                    .list();
        }
    }

    public occupancies saveOrUpdate(occupancies occ) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            occupancies managed = (occupancies) session.merge(occ);
            tx.commit();
            return managed;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void delete(occupancies occ) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            // If occ is detached, reattach or load by id
            if (occ.getPk() != null) {
                // If we have a surrogate key, fetch the managed entity and delete it
                occupancies managed = session.get(occupancies.class, occ.getPk());
                if (managed != null) session.remove(managed);
            } else {
                // try to find a matching row by natural key (hotel id + year + month + room)
                // fallback: attempt remove by merging then removing
                occupancies merged = (occupancies) session.merge(occ);
                session.remove(merged);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

}

