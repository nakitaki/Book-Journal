package bg.nbu.project_f104774.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bg.nbu.project_f104774.R;
import bg.nbu.project_f104774.adapter.BooksAdapter;
import bg.nbu.project_f104774.model.Book;
import bg.nbu.project_f104774.service.BooksService;

public class BooksSearchActivity extends AppCompatActivity {

    private EditText searchTitleEditText;
    private EditText searchAuthorEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;
    private List<Book> books = new ArrayList<>();
    private BooksService booksService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        recyclerView = findViewById(R.id.recycler_view);
        searchTitleEditText = findViewById(R.id.search_title);
        searchAuthorEditText = findViewById(R.id.search_author);
        searchButton = findViewById(R.id.search_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksAdapter = new BooksAdapter(this, books);
        recyclerView.setAdapter(booksAdapter);

        booksService = new BooksService();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = searchTitleEditText.getText().toString().trim();
                String author = searchAuthorEditText.getText().toString().trim();
                searchBooks(title, author);
            }
        });
    }

    private void searchBooks(String title, String author) {
        int previousSize = books.size();
        books.clear();
        booksAdapter.notifyItemRangeRemoved(0, previousSize);

        booksService.searchBooks(title, author, new BooksService.BooksServiceCallback() {
            @Override
            public void onSuccess(List<Book> newBooks) {
                updateBooksOnUiThread(newBooks);
            }

            @Override
            public void onError(final String errorMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BooksSearchActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void updateBooksOnUiThread(final List<Book> newBooks) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                books.addAll(newBooks);
                booksAdapter.notifyItemRangeInserted(0, newBooks.size());
            }
        });
    }
}
