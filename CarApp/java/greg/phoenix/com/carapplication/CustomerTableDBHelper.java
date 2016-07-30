package greg.phoenix.com.carapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CustomerTableDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CARDEALER.db";
    private static final int DATABASE_VERSION = 1;
    public static final String CUSTOMER_TABLE= "customer";
    public static final String CUSTOMER_COLUMN_ID = "_id";
    public static final String CUSTOMER_FIRST_NAME = "first_name";
    public static final String CUSTOMER_LAST_NAME = "last_naem";
    public static final String CAR_MAKE = "make";
    public static final String COST = "cost";

    public CustomerTableDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CUSTOMER_TABLE + "(" +
            CUSTOMER_COLUMN_ID + " INTEGER PRIMARY KEY, " +
            CUSTOMER_FIRST_NAME + " TEXT, " +
            CUSTOMER_LAST_NAME + " TEXT, " +
            CAR_MAKE + " TEXT, " +
            COST + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
        onCreate(db);
    }

    public boolean insertCustomer(String firstName, String lastName, String make, int cost ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_FIRST_NAME, firstName);
        contentValues.put(CUSTOMER_LAST_NAME, lastName);
        contentValues.put(CAR_MAKE, make);
        contentValues.put(COST, cost);
        db.insert(CUSTOMER_TABLE, null, contentValues);
        return true;
    }

    public boolean updateCustomer(Integer id, String firstName, String lastName, String make, int cost ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_FIRST_NAME, firstName);
        contentValues.put(CUSTOMER_LAST_NAME, lastName);
        contentValues.put(CAR_MAKE, make);
        contentValues.put(COST, cost);
        db.update(CUSTOMER_TABLE, contentValues, CUSTOMER_COLUMN_ID +  " = ? " , new String[]{Integer.toString(id)});
        return true;
    }

    public Cursor getCustomer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CUSTOMER_TABLE + " WHERE " +
                CUSTOMER_COLUMN_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }
    public Cursor getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CUSTOMER_TABLE, null );
        return res;
    }

    public Integer deleteCustomer(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CUSTOMER_TABLE,
                CUSTOMER_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }



}
