package bg.nbu.project_f104774.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bg.nbu.project_f104774.R;
import bg.nbu.project_f104774.service.MyService;

public class MainActivity extends AppCompatActivity {

    Button serviceButton;
    private boolean serviceRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button booksButton = findViewById(R.id.button_books);
        Button addButton = findViewById(R.id.button_add);

        booksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReviewsAllActivity.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BooksSearchActivity.class);
                startActivity(intent);
            }
        });

        serviceButton = findViewById(R.id.service_button);

    }

    public void toggleService(View view) {
        Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
        if (!serviceRunning) {
            // Start the service
            startService(serviceIntent);
            serviceButton.setText(R.string.stop_music);
            serviceRunning = true;
            Toast.makeText(this, "Music started", Toast.LENGTH_SHORT).show();
        } else {
            // Stop the service
            stopService(serviceIntent);
            serviceButton.setText(R.string.start_music);
            serviceRunning = false;
            Toast.makeText(this, "Music stopped", Toast.LENGTH_SHORT).show();
        }
    }
}
