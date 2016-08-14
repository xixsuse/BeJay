package rocks.itsnotrocketscience.bejay.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.squareup.sqlbrite.BriteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Observable;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

public class EventsDao {

    public static final String TABLE_NAME = "events";

    public interface Columns extends BaseColumns {
        String ORDER = "sortOrder";
        String PUBLISH = "publish";
        String UID = "uid";
        String CREATED = "created";
        String MODIFIED = "modified";
        String TITLE = "title";
        String APP_USER = "appUser";
    }

    private final BriteDatabase db;

    @Inject
    public EventsDao(BriteDatabase db) {
        this.db = db;
    }

    public Observable<List<Event>> all() {
        return db.createQuery(TABLE_NAME, "select * from " + TABLE_NAME).mapToList(this::fromCursor).first();
    }

    public Observable<ArrayList<Event>> save(ArrayList<Event> events) {
        return Observable.from(events)
                .filter(event -> db.insert(TABLE_NAME, toContentValues(event), CONFLICT_REPLACE) > 0)
                .collect(ArrayList::new, ArrayList::add);
    }

    private ContentValues toContentValues(Event event) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns._ID, event.getId());
        contentValues.put(Columns.ORDER, event.getOrder());
        contentValues.put(Columns.PUBLISH, event.getPublish());
        contentValues.put(Columns.UID, event.getUid());
        contentValues.put(Columns.CREATED, event.getCreated());
        contentValues.put(Columns.MODIFIED, event.getModified());
        contentValues.put(Columns.TITLE, event.getTitle());
        contentValues.put(Columns.APP_USER, event.getAppUser());
        return contentValues;
    }

    private Event fromCursor(Cursor cursor) {
        Event event = new Event();
        event.setId(cursor.getInt(cursor.getColumnIndex(Columns._ID)));
        event.setOrder(cursor.getInt(cursor.getColumnIndex(Columns.ORDER)));
        event.setPublish(cursor.getInt(cursor.getColumnIndex(Columns.PUBLISH)) != 0);
        event.setUid(cursor.getString(cursor.getColumnIndex(Columns.UID)));
        event.setCreated(cursor.getString(cursor.getColumnIndex(Columns.CREATED)));
        event.setModified(cursor.getString(cursor.getColumnIndex(Columns.MODIFIED)));
        event.setTitle(cursor.getString(cursor.getColumnIndex(Columns.TITLE)));
        event.setAppUser(cursor.getString(cursor.getColumnIndex(Columns.APP_USER)));

        return event;
    }
}
