package WorkingClasses;

import Objects.Category;
import Objects.User;

import java.util.List;

public class UserService {
    private static final UserDataAccessObject usersDao = new UserDataAccessObject();

    public UserService() {
    }

    public static User findUser(int id) {
        return usersDao.findUserById(id);
    }

    public static void rememberUser(User user) {
        usersDao.rememberUser(user);
    }

    public static void deleteUser(User user) {
        usersDao.deleteUser(user);
    }

    public static void updateUser(User user) {
        usersDao.updateUser(user);
    }

    public static List<User> findAllUsers() {
        return usersDao.findAllUsers();
    }

    public static Category findCategoryById(int id) {
        return usersDao.findCategoryById(id);
    }

    public static void saveOrUpdateUser(User user) {
        usersDao.saveOrUpdateUser(user);
    }
}
