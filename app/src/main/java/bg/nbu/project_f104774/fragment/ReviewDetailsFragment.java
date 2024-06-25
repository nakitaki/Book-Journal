package bg.nbu.project_f104774.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import bg.nbu.project_f104774.R;
import bg.nbu.project_f104774.activity.ReviewEditActivity;
import bg.nbu.project_f104774.database.MyDataBaseHelper;
import bg.nbu.project_f104774.model.BookReview;

public class ReviewDetailsFragment extends Fragment {

    private static final String ARG_BOOK_ID = "bookId";
    private int bookId;

    MyDataBaseHelper dbHelper;
    Button editButton;
    Button deleteButton;


    public static ReviewDetailsFragment newInstance(int bookId) {
        ReviewDetailsFragment fragment = new ReviewDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_details, container, false);

        deleteButton = view.findViewById(R.id.delete_button);
        editButton = view.findViewById(R.id.edit_button);

        if (getArguments() != null) {
            bookId = getArguments().getInt(ARG_BOOK_ID);

            // Fetch the BookReview by ID
            MyDataBaseHelper dbHelper = new MyDataBaseHelper(getActivity());
            BookReview bookReview = dbHelper.getBookReviewById(bookId);

            // Display book review details
            TextView nameTextView = view.findViewById(R.id.name_text_view);
            TextView authorTextView = view.findViewById(R.id.author_text_view);
            TextView summaryTextView = view.findViewById(R.id.summary_text_view);
            TextView rateTextView = view.findViewById(R.id.rate_text_view);

            nameTextView.setText(bookReview.getName());
            authorTextView.setText(bookReview.getAuthor());
            summaryTextView.setText(bookReview.getSummary());
            rateTextView.setText(String.valueOf(bookReview.getRate()));
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReview(bookId);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editReview(bookId);
            }
        });


        //TODO
        // Fetch and display review details based on bookId
        if (getArguments() != null) {
            bookId = getArguments().getInt(ARG_BOOK_ID);
            loadReviewDetails(view, bookId);
        }

        return view;
    }

    //TODO what is dis
    private void loadReviewDetails(View view, int bookId) {
        MyDataBaseHelper dbHelper = new MyDataBaseHelper(getActivity());
        BookReview bookReview = dbHelper.getBookReviewById(bookId);

        if (bookReview != null) {
            TextView nameTextView = view.findViewById(R.id.name_text_view);
            TextView authorTextView = view.findViewById(R.id.author_text_view);
            TextView summaryTextView = view.findViewById(R.id.summary_text_view);
            TextView rateTextView = view.findViewById(R.id.rate_text_view);

            nameTextView.setText(bookReview.getName());
            authorTextView.setText(bookReview.getAuthor());
            summaryTextView.setText(bookReview.getSummary());
            rateTextView.setText(String.valueOf(bookReview.getRate()));
        } else {
            Toast.makeText(getActivity(), "Error loading review details", Toast.LENGTH_SHORT).show();
        }
    }

    private void editReview(long reviewId) {
        // Example: Navigate to ReviewEditActivity passing the reviewId
        Intent intent = new Intent(getActivity(), ReviewEditActivity.class);
        intent.putExtra("reviewId", reviewId);
        startActivity(intent);
    }

    private void deleteReview(int reviewId) {
        // Create a confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this review?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performDelete(reviewId);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void performDelete(int reviewId) {
        MyDataBaseHelper dbHelper = new MyDataBaseHelper(getActivity());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Define the selection criteria
        String selection = MyDataBaseHelper.UID + "=?";
        // Specify the arguments in placeholder order
        String[] selectionArgs = { String.valueOf(reviewId) };

        int deletedRows = database.delete(MyDataBaseHelper.TABLE_NAME, selection, selectionArgs);

        if (deletedRows == 0) {
            Toast.makeText(getActivity(), "Review deletion failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Review deleted successfully", Toast.LENGTH_SHORT).show();
            // Navigate back to previous fragment or activity
            requireActivity().getSupportFragmentManager().popBackStack();
        }

        database.close();
    }



}
