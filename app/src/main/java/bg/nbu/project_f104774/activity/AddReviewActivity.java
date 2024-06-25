package bg.nbu.project_f104774.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

import bg.nbu.project_f104774.database.MyDataBaseHelper;
import bg.nbu.project_f104774.R;
import bg.nbu.project_f104774.model.Book;
import bg.nbu.project_f104774.model.VolumeInfo;

public class AddReviewActivity extends AppCompatActivity {

    EditText bookNameEditText, authorEditText, summaryEditText, rateEditText;
    Button saveButton;
    MyDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        bookNameEditText = findViewById(R.id.book_name_edit_text);
        authorEditText = findViewById(R.id.author_edit_text);
        summaryEditText = findViewById(R.id.summary_edit_text);
        rateEditText = findViewById(R.id.rate_edit_text);
        saveButton = findViewById(R.id.save_button);

        dbHelper = new MyDataBaseHelper(this);

        String bookJson = getIntent().getStringExtra("bookJson");
        if (bookJson != null) {
            try {
                Book book = Book.fromJson(new JSONObject(bookJson));
                VolumeInfo volumeInfo = book.getVolumeInfo();
                bookNameEditText.setText(volumeInfo.getTitle() != null ? volumeInfo.getTitle() : "No title");
                authorEditText.setText(volumeInfo.getAuthors() != null ? listToCommaSeparatedString(volumeInfo.getAuthors()) : "No authors");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReview();
            }
        });
    }

    private String listToCommaSeparatedString(List<String> list) {
        return list.stream().collect(Collectors.joining(", "));
    }

    private void saveReview() {
        String bookName = bookNameEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();
        String summary = summaryEditText.getText().toString().trim();
        String rateStr = rateEditText.getText().toString().trim();

        if (bookName.isEmpty() || author.isEmpty() || summary.isEmpty() || rateStr.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int rate = Integer.parseInt(rateStr);
        if (rate < 1 || rate > 5) {
            Toast.makeText(this, "Rate must be between 1 and 5", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDataBaseHelper.COLUMN_BOOK_NAME, bookName);
        values.put(MyDataBaseHelper.COLUMN_AUTHOR, author);
        values.put(MyDataBaseHelper.COLUMN_SUMMARY, summary);
        values.put(MyDataBaseHelper.COLUMN_RATE, rate);

        long newRowId = database.insert(MyDataBaseHelper.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving review", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Review saved with id: " + newRowId, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddReviewActivity.this, AllReviewsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);
            finish(); // close the activity
        }

        database.close();
    }
}
