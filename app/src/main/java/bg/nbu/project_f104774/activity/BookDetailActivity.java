package bg.nbu.project_f104774.activity;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        titleTextView = findViewById(R.id.detail_title);
        authorsTextView = findViewById(R.id.detail_authors);
        descriptionTextView = findViewById(R.id.detail_description);
        genreTextView = findViewById(R.id.detail_genre);

        String bookJson = getIntent().getStringExtra("bookJson");
        if (bookJson != null) {
            try {
                Book book = Book.fromJson(new JSONObject(bookJson));
                VolumeInfo volumeInfo = book.getVolumeInfo();
                titleTextView.setText(volumeInfo.getTitle() != null ? volumeInfo.getTitle() : "No title");
                authorsTextView.setText(volumeInfo.getAuthors() != null ? listToCommaSeparatedString(volumeInfo.getAuthors()) : "No authors");
                descriptionTextView.setText(volumeInfo.getDescription() != null ? volumeInfo.getDescription() : "No description");
                genreTextView.setText(volumeInfo.getCategories() != null ? listToCommaSeparatedString(volumeInfo.getCategories()) : "No genre");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String listToCommaSeparatedString(List<String> list) {
        return list.stream().collect(Collectors.joining(", "));
    }
}
