package bg.nbu.project_f104774.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import bg.nbu.project_f104774.model.BookReview;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    // Constants for database name and version
    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE_NAME = "book_review";

    // Column names
    public static final String UID = "_ID";
    public static final String COLUMN_BOOK_NAME = "name";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_RATE = "rate";

    // Constructor
    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE "
                + TABLE_NAME + " ("
                + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BOOK_NAME + " TEXT NOT NULL, "
                + COLUMN_AUTHOR + " TEXT NOT NULL, "
                + COLUMN_SUMMARY + " TEXT, "
                + COLUMN_RATE + " INTEGER NOT NULL CHECK (" + COLUMN_RATE + " BETWEEN 1 AND 5));";

        // Execute the SQL statement
        database.execSQL(SQL_CREATE_ITEMS_TABLE);
    }

    // Upgrade the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create a new instance of the table
        onCreate(db);
    }

    public void addRecipe(BookReview bookReview) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_NAME, bookReview.getName());
        values.put(COLUMN_AUTHOR, bookReview.getAuthor());
        values.put(COLUMN_SUMMARY, bookReview.getSummary());
        values.put(COLUMN_RATE, bookReview.getRate());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }



}
