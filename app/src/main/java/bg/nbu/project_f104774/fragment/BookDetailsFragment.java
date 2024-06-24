package bg.nbu.project_f104774.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import bg.nbu.project_f104774.R;
import bg.nbu.project_f104774.database.MyDataBaseHelper;
import bg.nbu.project_f104774.model.BookReview;

public class BookDetailsFragment extends Fragment {
    BookReview bookReview;
    MyDataBaseHelper dbHelper;
    SQLiteDatabase database;

    public BookDetailsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.item_book_review, container, false);
        // Създайте списък със студенти

//        final ArrayList<Students> students = new ArrayList<>();
//        students.add(new Students("Иван Иванов", "112233"));
//        students.add(new Students("Георги Георгиев", "992244"));
//        students.add(new Students("Стефан Стефанов", "882255"));
//        StudentsAdapter adapter = new StudentsAdapter(getActivity(), students,  R.color.specialty_informatics);
//        final ListView listView = rootview.findViewById(R.id.list);
//        listView.setAdapter(adapter);
        return rootview;
    }
}