package itsoftware.datdot.bonch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UsersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_activity);

        Button button = findViewById(R.id.button);
        Button button1 = findViewById(R.id.button2);
        Button button2 = findViewById(R.id.button3);
        Button button3 = findViewById(R.id.button4);

        OnClickListenerListener listener = new OnClickListenerListener();
        button.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
    }

    private class OnClickListenerListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            TextView myTextView = findViewById(R.id.textView3);

            switch (view.getId()) {
                case R.id.button:
                    myTextView.setText("Вы использовали 1-й способ");
                    break;
                case R.id.button2:
                    myTextView.setText("Вы использовали 2-й способ");
                    break;
                case R.id.button3:
                    myTextView.setText("Вы использовали 3-й способ");
                    break;
                case R.id.button4:
                    myTextView.setText("Вы использовали 4-й способ");
                    break;
            }
        }
    }
}
