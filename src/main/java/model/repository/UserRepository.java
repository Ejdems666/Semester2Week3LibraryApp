package model.repository;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by adam on 26/02/2017.
 */
public class UserRepository implements Repository<User> {
    private final HyggeDb db;

    private static Repository<User> instance;

    public static Repository<User> getInstance(HyggeDb db) {
        if (instance == null) {
            return instance = new UserRepository(db);
        } else {
            return instance;
        }
    }
    private UserRepository(HyggeDb db) {
        this.db = db;
        instance = this;
    }

    public User getById(int id) {
        Selection selection = new Selection("user");
        selection.where("id=?",id);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapUser(rs);
    }

    private User mapUser(ResultSet rs) {
        User user = null;
        try {
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setCreatedAt(rs.getDate("created_at"));
                user.setStatus(rs.getInt("status"));
                user.setType(rs.getInt("type"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setSalt(rs.getString("salt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Collection<User> findAll() {
        return null;
    }

    public Collection<User> findBy(Condition condition) {
        Selection selection = new Selection("user");
        selection.setWhere(condition);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapUsers(rs);
    }

    private Collection<User> mapUsers(ResultSet rs) {
        Collection<User> users = new ArrayList<User>();
        User user = mapUser(rs);
        while (user != null) {
            users.add(user);
            user = mapUser(rs);
        }
        return users;
    }

    public void persist(User entity) {

    }

    public void persistAndFlush(User entity) {

    }

    public void flush() {

    }
}
