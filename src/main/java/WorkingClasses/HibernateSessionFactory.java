package WorkingClasses;

import Objects.Category;
import Objects.DateSpent;
import Objects.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    public static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        Configuration configuration = createConfiguration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(DateSpent.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public static Configuration createConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", System.getenv("DATABASE_URL"));
        configuration.setProperty("hibernate.connection.username", System.getenv("DATABASE_USERNAME"));
        configuration.setProperty("hibernate.connection.password", System.getenv("DATABASE_PASSWORD"));
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        configuration.setProperty("show_sql", "false");
        return configuration;
    }
}
