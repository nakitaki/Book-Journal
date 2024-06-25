package bg.nbu.project_f104774.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookResponse {
    @SerializedName("items")
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
