package bg.nbu.project_f104774;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        // Display message with the name of the activity
        TextView textView = findViewById(R.id.text_view_books);
        textView.setText("Welcome to BooksActivity");
    }
}
