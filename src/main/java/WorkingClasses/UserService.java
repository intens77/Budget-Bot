package WorkingClasses;

import Objects.Category;
import Objects.User;

import java.util.List;

public class UserService {
    private final UserDataAccessObject usersDao = new UserDataAccessObject();

    public UserService() {
    }

    public User findUser(int id) {
        return usersDao.findUserById(id);
    }

    public void rememberUser(User user) {
        usersDao.rememberUser(user);
    }

    public void deleteUser(User user) {
        usersDao.deleteUser(user);
    }

    public void updateUser(User user) {
        usersDao.updateUser(user);
    }

    public List<User> findAllUsers() {
        return usersDao.findAllUsers();
    }

    public Category findCategoryById(int id) {
        return usersDao.findCategoryById(id);
    }
}
