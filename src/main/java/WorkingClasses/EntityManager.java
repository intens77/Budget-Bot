package WorkingClasses;

import Objects.Category;
import Objects.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EntityManager {
    public static User findUserById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        transaction.commit();
        session.close();
        return user;
    }

    public static Category findCategoryById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Category category = session.get(Category.class, id);
        transaction.commit();
        session.close();
        return category;
    }

    public static void updateUser(User user) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void updateCategory(Category category) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(category);
            transaction.commit();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void saveUser(User user) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void saveCategory(Category category) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(category);
            transaction.commit();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(User user) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void deleteCategory(Category category) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(category);
            transaction.commit();
            session.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static List<User> findAllUsers() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) session.createQuery("FROM User").list();
        session.close();
        return users;
    }

    public static User findUserByTelegramId(String id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        User user = (User) session.createQuery("from User where telegramId = :id")
                .setParameter("id", id).list().get(0);
        session.close();
        return user;
    }
}
