package bg.nbu.project_f104774.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

import bg.nbu.project_f104774.R;
import bg.nbu.project_f104774.model.Book;
import bg.nbu.project_f104774.model.VolumeInfo;

public class BookDetailActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView authorsTextView;
    private TextView descriptionTextView;
    private TextView genreTextView;

    Button addReviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        titleTextView = findViewById(R.id.detail_title);
        authorsTextView = findViewById(R.id.detail_authors);
        descriptionTextView = findViewById(R.id.detail_description);
        genreTextView = findViewById(R.id.detail_genre);
        addReviewButton = findViewById(R.id.add_review_button);

        String bookJson = getIntent().getStringExtra("bookJson");
        if (bookJson != null) {
            try {
                Book book = Book.fromJson(new JSONObject(bookJson));
                VolumeInfo volumeInfo = book.getVolumeInfo();
                titleTextView.setText(volumeInfo.getTitle() != null ? volumeInfo.getTitle() : "No title");
                authorsTextView.setText(volumeInfo.getAuthors() != null ? listToCommaSeparatedString(volumeInfo.getAuthors()) : "No authors");
                descriptionTextView.setText(volumeInfo.getDescription() != null ? volumeInfo.getDescription() : "No description");
                genreTextView.setText(volumeInfo.getCategories() != null ? listToCommaSeparatedString(volumeInfo.getCategories()) : "No genre");

                addReviewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BookDetailActivity.this, AddReviewActivity.class);
                        intent.putExtra("bookJson", bookJson);
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String listToCommaSeparatedString(List<String> list) {
        return list.stream().collect(Collectors.joining(", "));
    }
}
