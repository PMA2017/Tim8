package takeyourseat.db;

import android.content.Context;
import android.database.SQLException;

import java.util.List;

import takeyourseat.model.User;

/**
 * Created by anica on 16.6.2017.
 */

public class UserRepoClass implements Crud {

    private DatabaseHelper helper;

    public UserRepoClass(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {

        int index = -1;

        User object = (User) item;
        try {
            //index = helper.getUserDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int update(Object item) {

        int index = -1;

        User object = (User) item;

        try {
           // helper.getUserDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int delete(Object item) {

        int index = -1;

        User object = (User) item;

        try {
           // helper.getUserDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;

    }

    @Override
    public Object findById(int id) {

        User wishList = null;
        try {
            //wishList = helper.getUserDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;

    }

    @Override
    public List<?> findAll() {

        List<User> items = null;

        try {
            //items = helper.getUserDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
