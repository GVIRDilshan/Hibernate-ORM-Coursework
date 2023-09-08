package dao.custom.impl;

import dao.custom.ReservationDAO;
import entity.Reservation;
import entity.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import util.FactoryConfiguration;

import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public boolean add(Reservation reservation) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(reservation);
        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public Reservation search(String idOrName) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Reservation reservation = session.get(Reservation.class, idOrName);
        session.close();
        return reservation;

    }

    @Override
    public boolean update(Reservation reservation) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(reservation);
        transaction.commit();

        session.close();
        return true;

    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Reservation reservation = session.get(Reservation.class, id);
        session.delete(reservation);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Reservation> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        NativeQuery query = session.createNativeQuery("SELECT  * FROM reservation");
        query.addEntity(Reservation.class);
        List<Reservation> list = query.list();
        session.close();
        return list;
    }

    @Override
    public List<Object[]> getResChart() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        NativeQuery query = session.createNativeQuery("SELECT COUNT(date) , MONTH(date) as months FROM reservation GROUP BY MONTH(date) ORDER BY months ASC");
        List<Object[]> list = query.list();
        session.close();
        return list;
    }

}
