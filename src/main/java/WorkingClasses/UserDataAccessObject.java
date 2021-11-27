package WorkingClasses;

import Objects.Category;
import Objects.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDataAccessObject {
    public User findUserById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(User.class, id);
    }

    public void updateUser(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    public void rememberUser(User user) {
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

    public void saveOrUpdateUser(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
        session.close();
    }

    public void deleteUser(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public Category findCategoryById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Category.class, id);
    }

    public List<User> findAllUsers() {
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) HibernateSessionFactory.getSessionFactory().openSession()
                .createQuery("FROM User").list();
        return users;
    }
}
