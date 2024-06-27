package bg.nbu.project_f104774.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import bg.nbu.project_f104774.R;
import bg.nbu.project_f104774.adapter.ReviewAdapter;
import bg.nbu.project_f104774.database.MyDataBaseHelper;
import bg.nbu.project_f104774.model.BookReview;

public class ReviewsDataFragment extends Fragment {

    TextView textView;
    ArrayList<BookReview> dataList;
    ListView listView;
    MyDataBaseHelper dbHelper;
    SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_data, container, false);

        textView = view.findViewById(R.id.name_text);
        listView = view.findViewById(R.id.list);
        dataList = new ArrayList<>();

        dbHelper = new MyDataBaseHelper(getActivity());
        database = dbHelper.getReadableDatabase();

        Cursor dataCursor = database.rawQuery("SELECT * FROM " + MyDataBaseHelper.TABLE_NAME, null);

        if (dataCursor.getCount() == 0) {
            Toast.makeText(getActivity(), "The database is empty!", Toast.LENGTH_LONG).show();
        } else {
            while (dataCursor.moveToNext()) {
                int id = dataCursor.getInt(dataCursor.getColumnIndexOrThrow(MyDataBaseHelper.UID));
                String name = dataCursor.getString(dataCursor.getColumnIndexOrThrow(MyDataBaseHelper.COLUMN_BOOK_NAME));
                String author = dataCursor.getString(dataCursor.getColumnIndexOrThrow(MyDataBaseHelper.COLUMN_AUTHOR));
                String summary = dataCursor.getString(dataCursor.getColumnIndexOrThrow(MyDataBaseHelper.COLUMN_SUMMARY));
                int rate = dataCursor.getInt(dataCursor.getColumnIndexOrThrow(MyDataBaseHelper.COLUMN_RATE));

                BookReview bookReview = new BookReview(id, name, author, summary, rate);
                dataList.add(bookReview);
            }

            ReviewAdapter adapter = new ReviewAdapter(getActivity(), dataList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BookReview selectedBookReview = dataList.get(position);

                    int bookId = selectedBookReview.getId();

//                    Toast.makeText(getActivity(), "Selected: " + selectedBookReview.getName(), Toast.LENGTH_SHORT).show();

                    ReviewDetailsFragment reviewDetailsFragment = ReviewDetailsFragment.newInstance(bookId);

                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, reviewDetailsFragment)
                            .addToBackStack(null)
                            .commit();
                }

            });



        }
        dataCursor.close();

        return view;
    }

    @Override
    public void onDestroy() {
        database.close();
        dbHelper.close();
        super.onDestroy();
    }
}
