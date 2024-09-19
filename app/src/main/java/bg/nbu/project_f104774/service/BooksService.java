package bg.nbu.project_f104774.service;

import java.util.List;

import bg.nbu.project_f104774.model.Book;
import bg.nbu.project_f104774.model.BookResponse;
import bg.nbu.project_f104774.network.ApiClient;
import bg.nbu.project_f104774.network.BooksApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksService {
    private static final String API_KEY = "";
    private final BooksApiService apiService;

    public interface BooksServiceCallback {
        void onSuccess(List<Book> books);
        void onError(String errorMessage);
    }

    public BooksService() {
        apiService = ApiClient.getRetrofitInstance().create(BooksApiService.class);
    }

    public void searchBooks(String title, String author, final BooksServiceCallback callback) {
        String query;
        if (!title.isEmpty() && !author.isEmpty()) {
            query = title + " intitle:" + title + " inauthor:" + author;
        } else if (!title.isEmpty()) {
            query = "intitle:" + title;
        } else if (!author.isEmpty()) {
            query = "inauthor:" + author;
        } else {
            callback.onError("Both title and author are empty");
            return;
        }

        Call<BookResponse> call = apiService.getBooks(query, title, author, API_KEY);

        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful()) {
                    List<Book> newBooks = response.body().getBooks();
                    if (newBooks != null && !newBooks.isEmpty()) {
                        callback.onSuccess(newBooks);
                    } else {
                        callback.onError("No books found");
                    }
                } else {
                    callback.onError("Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                callback.onError("Network request failed: " + t.getMessage());
            }
        });
    }
}
