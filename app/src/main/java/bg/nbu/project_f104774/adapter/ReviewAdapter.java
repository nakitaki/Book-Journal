package bg.nbu.project_f104774.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bg.nbu.project_f104774.model.BookReview;
import bg.nbu.project_f104774.R;

public class ReviewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BookReview> bookReviews;

    public ReviewAdapter(Context context, ArrayList<BookReview> bookReviews) {
        this.context = context;
        this.bookReviews = bookReviews;
    }

    @Override
    public int getCount() {
        return bookReviews.size();
    }

    @Override
    public Object getItem(int position) {
        return bookReviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bookReviews.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book_review, parent, false);
        }

        BookReview bookReview = (BookReview) getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.name_text_view);
        TextView authorTextView = convertView.findViewById(R.id.author_text_view);
//        TextView summaryTextView = convertView.findViewById(R.id.summary_text_view);
//        TextView rateTextView = convertView.findViewById(R.id.rate_text_view);

        nameTextView.setText(bookReview.getName());
        authorTextView.setText(bookReview.getAuthor());
//        summaryTextView.setText(bookReview.getSummary());
//        rateTextView.setText(String.valueOf(bookReview.getRate()));

        return convertView;
    }
}
