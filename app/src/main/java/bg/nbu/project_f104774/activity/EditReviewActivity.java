package bg.nbu.project_f104774.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bg.nbu.project_f104774.R;
import bg.nbu.project_f104774.database.MyDataBaseHelper;
import bg.nbu.project_f104774.fragment.ReviewDetailsFragment;
import bg.nbu.project_f104774.model.BookReview;

public class EditReviewActivity extends AppCompatActivity {

    TextView bookName, author;
    EditText editSummary, editRate;
    Button saveButton;
    MyDataBaseHelper dbHelper;
    long reviewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review);

        bookName = findViewById(R.id.edit_book_name);
        author = findViewById(R.id.edit_author);
        editSummary = findViewById(R.id.edit_summary);
        editRate = findViewById(R.id.edit_rate);
        saveButton = findViewById(R.id.save_button);

        dbHelper = new MyDataBaseHelper(this);

        // Retrieve the reviewId passed from DetailsFragment or wherever
        reviewId = getIntent().getLongExtra("reviewId", -1);

        if (reviewId == -1) {
            Toast.makeText(this, "Error: Review ID not provided", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Fetch current review details and populate EditText fields
        populateFields();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateReview(reviewId);
            }
        });


    }

    private void populateFields() {
        // Fetch current review details from database and populate EditText fields
        BookReview bookReview = dbHelper.getBookReviewById((int) reviewId);
        if (bookReview != null) {
            bookName.setText(bookReview.getName());
            author.setText(bookReview.getAuthor());
            editSummary.setText(bookReview.getSummary());
            editRate.setText(String.valueOf(bookReview.getRate()));
        } else {
            Toast.makeText(this, "Error loading review details", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateReview(long reviewId) {
        String bookName = this.bookName.getText().toString().trim();
        String author = this.author.getText().toString().trim();
        String summary = editSummary.getText().toString().trim();
        String rateStr = editRate.getText().toString().trim();

        if (bookName.isEmpty() || author.isEmpty() || summary.isEmpty() || rateStr.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int rate = Integer.parseInt(rateStr);
        if (rate < 1 || rate > 5) {
            Toast.makeText(this, "Rate must be between 1 and 5", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update review in database
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDataBaseHelper.COLUMN_BOOK_NAME, bookName);
        values.put(MyDataBaseHelper.COLUMN_AUTHOR, author);
        values.put(MyDataBaseHelper.COLUMN_SUMMARY, summary);
        values.put(MyDataBaseHelper.COLUMN_RATE, rate);

        // Define the selection criteria
        String selection = MyDataBaseHelper.UID + "=?";
        // Specify the arguments in placeholder order
        String[] selectionArgs = { String.valueOf(reviewId) };

        int updatedRows = database.update(MyDataBaseHelper.TABLE_NAME, values, selection, selectionArgs);

        if (updatedRows == 0) {
            Toast.makeText(this, "Error updating review", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Review updated successfully", Toast.LENGTH_SHORT).show();

            // Close database connection
            database.close();

            // Navigate back to DetailsFragment
            navigateToDetailsFragment(reviewId);
        }
    }

    private void navigateToDetailsFragment(long reviewId) {
        // Create an instance of DetailsFragment with arguments
        ReviewDetailsFragment reviewDetailsFragment = ReviewDetailsFragment.newInstance((int) reviewId);

        // Replace the current fragment (EditReviewActivity) with DetailsFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, reviewDetailsFragment)
                .addToBackStack(null)  // Optional: Adds the transaction to the back stack
                .commit();
    }
}
