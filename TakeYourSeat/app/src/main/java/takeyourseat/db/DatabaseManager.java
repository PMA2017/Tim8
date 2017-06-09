package takeyourseat.db;

import android.content.Context;


/**
 * Created by anica on 9.6.2017.
 */

public class DatabaseManager {

    static private DatabaseManager instance;
    private Context ctx;

    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseHelper helper;

    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    public DatabaseHelper getHelper() {
        return helper;
    }

}
