package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS Users (" +
                    "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Name VARCHAR(64), " +
                    "Last_name VARCHAR(64), " +
                    "Age INT(255))").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS Users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User where id = " + id).executeUpdate(); //session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("from User ").getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
