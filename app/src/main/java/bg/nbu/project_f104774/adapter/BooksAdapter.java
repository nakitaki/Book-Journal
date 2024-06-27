package bg.nbu.project_f104774.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bg.nbu.project_f104774.R;
import bg.nbu.project_f104774.activity.BookDetailActivity;
import bg.nbu.project_f104774.model.Book;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    private List<Book> books;
    private Context context;

    public BooksAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        if (book != null && book.getVolumeInfo() != null) {

            String title = book.getVolumeInfo().getTitle();
            holder.titleTextView.setText(title != null ? title : "No title");

            List<String> authors = book.getVolumeInfo().getAuthors();
            if (authors != null && !authors.isEmpty()) {
                holder.authorsTextView.setText(TextUtils.join(", ", authors));
            } else {
                holder.authorsTextView.setText("No authors");
            }

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("bookJson", book.toJson());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorsTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.book_title);
            authorsTextView = itemView.findViewById(R.id.book_authors);
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
