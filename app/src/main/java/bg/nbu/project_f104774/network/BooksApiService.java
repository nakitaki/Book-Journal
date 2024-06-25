package bg.nbu.project_f104774.network;

import bg.nbu.project_f104774.model.BookResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BooksApiService {
    @GET("volumes")
    Call<BookResponse> getBooks(
            @Query("q") String query,
            @Query("intitle") String title,
            @Query("inauthor") String author,
            @Query("key") String apiKey
    );
}
