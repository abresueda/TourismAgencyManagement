package business;

import core.Helper;
import dao.UserDao;
import entity.User;

import java.util.ArrayList;

public class UserManager {
    private final UserDao userDao = new UserDao();
    User user;

    public User findByLogin(String username, String password) {
        return this.userDao.findByLogin(username,password);
    }

    public ArrayList<User> findAll() {
        return this.userDao.findAll();
    }

    public User getById(int id) {
        return this.userDao.getById(id);
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<User> userList) {
        ArrayList<Object[]> userObjList = new ArrayList<>();
        for (User obj: userList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getUsername();
            rowObject[i++] = obj.getPassword();
            rowObject[i++] = obj.getRole();
            userObjList.add(rowObject);
        }
        return userObjList;
    }

    public boolean save(User user) {
        if (this.getById(user.getId()) != null) {
            Helper.showMessage("error");
            return false;
        }
        return this.userDao.save(user);
    }

    public boolean update(User user) {
        if (this.getById(user.getId()) == null) {
            Helper.showMessage(user.getId() + " ID kayıtlı kullanıcı bulunamadı.");
            return false;
        }
        return this.userDao.update(user);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage(id + " ID kayıtlı kullanıcı bulunamadı. ");
            return false;
        }
        return this.userDao.delete(id);
    }

    public ArrayList<User> searchForTable(int userId, String role) {
        String select = "SELECT * FROM public.user";
        ArrayList<String> whereList = new ArrayList<>();

        if (userId != 0) {
            whereList.add("user_id = " + userId);
        }
        if (!role.equalsIgnoreCase("all")) {
            whereList.add("LOWER(user_role) = '" + role.toLowerCase() + "'");
        }

        if (!whereList.isEmpty()) {
            select+= " WHERE " + String.join(" AND ", whereList);
        }
        return this.userDao.selectByQuery(select);
    }

}
