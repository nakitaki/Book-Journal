package bg.nbu.project_f104774.activity;

import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import bg.nbu.project_f104774.database.MyDataBaseHelper;
import bg.nbu.project_f104774.R;
import bg.nbu.project_f104774.model.Book;
import bg.nbu.project_f104774.model.VolumeInfo;

public class ReviewAddActivity extends AppCompatActivity {

    EditText bookNameEditText, authorEditText, summaryEditText, rateEditText;
    Button saveButton;
    MyDataBaseHelper dbHelper;
    Handler handler;

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

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_REVIEW_SAVED:
                        handleReviewSaved((Long) msg.obj);
                        break;
                    case MSG_ERROR:
                        handleSaveError();
                        break;
                    default:
                        break;
                }
            }
        };

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReview();
            }
        });
    }

    private static final int MSG_REVIEW_SAVED = 1;
    private static final int MSG_ERROR = 2;

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(MyDataBaseHelper.COLUMN_BOOK_NAME, bookName);
                values.put(MyDataBaseHelper.COLUMN_AUTHOR, author);
                values.put(MyDataBaseHelper.COLUMN_SUMMARY, summary);
                values.put(MyDataBaseHelper.COLUMN_RATE, rate);

                long newRowId = database.insert(MyDataBaseHelper.TABLE_NAME, null, values);

                if (newRowId == -1) {
                    handler.sendEmptyMessage(MSG_ERROR);
                } else {
                    Message message = handler.obtainMessage(MSG_REVIEW_SAVED, newRowId);
                    handler.sendMessage(message);
                }

                database.close();
            }
        }).start();
    }

    private void handleReviewSaved(long newRowId) {
        Toast.makeText(this, "Review saved successfully", Toast.LENGTH_SHORT).show();

        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent allReviewsIntent = new Intent(this, ReviewsAllActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(mainIntent);
        stackBuilder.addNextIntent(allReviewsIntent);

        stackBuilder.startActivities();
        finish();
    }

    private void handleSaveError() {
        Toast.makeText(this, "Error with saving review", Toast.LENGTH_SHORT).show();
    }

    private String listToCommaSeparatedString(List<String> list) {
        return String.join(", ", list);
    }
}
