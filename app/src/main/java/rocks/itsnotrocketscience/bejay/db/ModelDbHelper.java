package rocks.itsnotrocketscience.bejay.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import rocks.itsnotrocketscience.bejay.dao.EventsDao;

public class ModelDbHelper extends SQLiteOpenHelper {

    public static final String NAME = "models";
    public static final int VERSION = 1;

    public static final String CREATE_TABLE_EVENTS = "create table " + EventsDao.TABLE_NAME + "(" +
            EventsDao.Columns._ID + " integer primary key not null, " +
            EventsDao.Columns.ORDER + " integer, " +
            EventsDao.Columns.PUBLISH + " integer, " +
            EventsDao.Columns.UID + " text ," +
            EventsDao.Columns.CREATED + " text, " +
            EventsDao.Columns.MODIFIED + " text, " +
            EventsDao.Columns.TITLE + " text, " +
            EventsDao.Columns.APP_USER + " text);";


    public ModelDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ModelDbHelper(Context context) {
        this(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
