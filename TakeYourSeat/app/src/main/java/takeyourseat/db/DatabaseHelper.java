package takeyourseat.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.anica.takeyourseat.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


import java.sql.SQLException;

import takeyourseat.model.User;


/**
 * Created by anica on 9.6.2017.
 */


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    //Dajemo ime bazi
    private static final String DATABASE_NAME    = "ormlite.db";

    //i pocetnu verziju baze. Obicno krece od 1
    private static final int    DATABASE_VERSION = 1;

    private Dao<User, Integer> userDao = null;

    //Potrebno je dodati konstruktor zbog pravilne inicijalizacije biblioteke
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    //Prilikom kreiranja baze potrebno je da pozovemo odgovarajuce metode biblioteke
    //prilikom kreiranja moramo pozvati TableUtils.createTable za svaku tabelu koju imamo
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
        }
    }


    //kada zelimo da izmenomo tabele, moramo pozvati TableUtils.dropTable za sve tabele koje imamo
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
        }
    }

    //jedan Dao objekat sa kojim komuniciramo. Ukoliko imamo vise tabela
    //potrebno je napraviti Dao objekat za svaku tabelu
    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }

        return userDao;
    }

    //obavezno prilikom zatvarnaj rada sa bazom osloboditi resurse
    @Override
    public void close() {
        userDao = null;
        super.close();
    }
}