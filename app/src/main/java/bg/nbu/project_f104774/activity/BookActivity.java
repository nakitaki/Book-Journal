package bg.nbu.project_f104774.activity;

import android.os.Bundle;
import android.util.Log;
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
import bg.nbu.project_f104774.model.BookResponse;
import bg.nbu.project_f104774.network.ApiClient;
import bg.nbu.project_f104774.network.BooksApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {

    private static final String API_KEY = "AIzaSyADqcs2PchZqhsQsa7QW84D5ydBwNRWtCg";
    private EditText searchTitleEditText;
    private EditText searchAuthorEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;
    private List<Book> books = new ArrayList<>();

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
        // Clear current books list
        int previousSize = books.size();
        books.clear();
        booksAdapter.notifyItemRangeRemoved(0, previousSize); // Notify adapter about removed items

        String query;
        if (!title.isEmpty() && !author.isEmpty()) {
            query = title + " intitle:" + title + " inauthor:" + author;
        } else if (!title.isEmpty()) {
            query = "intitle:" + title;
        } else if (!author.isEmpty()) {
            query = "inauthor:" + author;
        } else {
            Log.e("BookActivity", "Both title and author are empty");
            return;
        }

        Log.d("BookActivity", "Query: " + query);

        BooksApiService service = ApiClient.getRetrofitInstance().create(BooksApiService.class);
        Call<BookResponse> call = service.getBooks(query, title, author, API_KEY);

        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful()) {
                    List<Book> newBooks = response.body().getBooks();
                    if (newBooks != null && !newBooks.isEmpty()) {
                        Log.d("BookActivity", "Number of books found: " + newBooks.size());
                        updateBooksOnUiThread(newBooks);
                    } else {
                        Log.d("BookActivity", "No books found");
                        displayNoBooksFoundMessage();
                    }
                } else {
                    Log.e("BookActivity", "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Log.e("BookActivity", "Network request failed: " + t.getMessage());
            }
        });
    }

    private void updateBooksOnUiThread(final List<Book> newBooks) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                books.addAll(newBooks);
                booksAdapter.notifyItemRangeInserted(0, newBooks.size()); // Notify adapter about inserted items
            }
        });
    }

    private void displayNoBooksFoundMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BookActivity.this, "No books found", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
