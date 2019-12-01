package itsoftware.datdot.bonch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        int a = getIntent().getIntExtra("buttonId", -1);
        Toast.makeText(this, "a = " + a, Toast.LENGTH_SHORT).show();
    }
}
