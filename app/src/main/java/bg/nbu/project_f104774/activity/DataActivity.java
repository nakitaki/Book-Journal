package bg.nbu.project_f104774.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import bg.nbu.project_f104774.R;
import bg.nbu.project_f104774.fragment.DataFragment;

public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, new DataFragment())
                    .commit();
        }
    }
}
