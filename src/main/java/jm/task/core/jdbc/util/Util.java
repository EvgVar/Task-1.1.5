package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/katadb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getHibernateConnection() {
        try {
            Configuration configuration = new Configuration()
                    .setProperty(Environment.DRIVER, DRIVER)
                    .setProperty(Environment.URL, URL)
                    .setProperty(Environment.USER, USERNAME)
                    .setProperty(Environment.PASS, PASSWORD)
                    .setProperty(Environment.DIALECT, DIALECT);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.addAnnotatedClass(User.class).buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}
