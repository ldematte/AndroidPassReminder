package it.sad.sii.AndroidPassReminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DBAccess extends SQLiteOpenHelper {
    private static final String TAG = DBAccess.class.getSimpleName();

    private static final String DB_NAME = "PassReminder.db";
    private static final int DB_VERSION = 2;

    private static DBAccess instance = null;
    private Context context;

    private DBAccess(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static DBAccess getInstance(Context context) {
        if (instance == null)
            instance = new DBAccess(context);
        return instance;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "DB deleted");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE reminders (" +
                "  cardid   text," +
                "  ctype    text," +
                "  checktime integer," +
                "  causal_id integer," +
                "  cnumber   text," +
                "  name      text);");
        Log.d(TAG, "DB created");
    }

    public int insertReminder(Reminder reminder) {
        SQLiteDatabase db = null;
        int ret;
        try {
            db = getWritableDatabase();
            ContentValues values = new ContentValues(6);
            values.put("cardid", reminder.getCardid());
            values.put("ctype", reminder.getCtype());
            values.put("checktime", reminder.getChecktime());
            values.put("causal_id", reminder.getCausalId());
            values.put("cnumber", reminder.getCnumber());
            values.put("name", reminder.getName());
            ret = (int)db.insertOrThrow("reminders", null, values);
            Log.d(TAG, "insertReminder() done");
        } catch (Exception e) {
            Log.d(TAG, "Error insertReminder(): " + e.getMessage());
            ret = -1;
        } finally {
            if (db != null)
                db.close();
            close();
        }
        return ret;
    }

    public List<Reminder> getReminders(String cardid) {
        SQLiteDatabase db = null;
        List<Reminder> ret = new ArrayList<Reminder>();
        Cursor c = null;
        try {
            db = getReadableDatabase();

            c = db.rawQuery("SELECT * FROM reminders WHERE cardid = '" + cardid + "' ORDER BY checktime DESC", null);
            while (c.moveToNext()) {
                ret.add(new Reminder(c.getLong(c.getColumnIndex("checktime")),
                                     c.getString(c.getColumnIndex("cnumber")),
                                     c.getString(c.getColumnIndex("name")),
                                     c.getInt(c.getColumnIndex("causal_id")),
                                     c.getString(c.getColumnIndex("cardid")),
                                     c.getString(c.getColumnIndex("ctype"))));
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Error reading reminders(): " + e.getMessage());
            ret = null;
        } finally {
            if (c != null)
                c.close();
            if (db != null)
                db.close();
            close();
        }
        return ret;
    }
}