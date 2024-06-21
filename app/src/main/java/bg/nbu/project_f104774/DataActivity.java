package bg.nbu.project_f104774;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {

    TextView textView;
    ArrayList<BookReview> dataList;
    ListView listView;
    MyDataBaseHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        textView = findViewById(R.id.name_text);
        listView = findViewById(R.id.list);
        dataList = new ArrayList<>();

        // Initialize MyDataBaseHelper
        dbHelper = new MyDataBaseHelper(this);

        // Open database for reading and writing
        database = dbHelper.getReadableDatabase();

        // Retrieve data using rawQuery() method
        Cursor dataCursor = database.rawQuery("SELECT * FROM " + MyDataBaseHelper.TABLE_NAME, null);

        if (dataCursor.getCount() == 0) {
            Toast.makeText(DataActivity.this, "The database is empty!", Toast.LENGTH_SHORT).show();
        } else {
            while (dataCursor.moveToNext()) {
                int id = dataCursor.getInt(dataCursor.getColumnIndexOrThrow(MyDataBaseHelper.UID));
                String name = dataCursor.getString(dataCursor.getColumnIndexOrThrow(MyDataBaseHelper.COLUMN_BOOK_NAME));
                String author = dataCursor.getString(dataCursor.getColumnIndexOrThrow(MyDataBaseHelper.COLUMN_AUTHOR));
                String summary = dataCursor.getString(dataCursor.getColumnIndexOrThrow(MyDataBaseHelper.COLUMN_SUMMARY));
                int rate = dataCursor.getInt(dataCursor.getColumnIndexOrThrow(MyDataBaseHelper.COLUMN_RATE));

                BookReview bookReview = new BookReview(id, name, author, summary, rate);
                dataList.add(bookReview);
            }

            BookReviewAdapter adapter = new BookReviewAdapter(this, dataList);
            listView.setAdapter(adapter);
        }
        dataCursor.close();
    }

    @Override
    protected void onDestroy() {
        // Close the database connection
        database.close();
        dbHelper.close();
        super.onDestroy();
    }
}
