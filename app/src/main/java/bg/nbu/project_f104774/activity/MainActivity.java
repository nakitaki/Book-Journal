package bg.nbu.project_f104774.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import bg.nbu.project_f104774.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons by their IDs
        Button buttonBooks = findViewById(R.id.button_books);
        Button buttonAdd = findViewById(R.id.button_add);

        // Set OnClickListener for the first button to start BooksActivity
        buttonBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for the second button to start AddActivity
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddReviewActivity.class);
                startActivity(intent);
            }
        });
    }
}
