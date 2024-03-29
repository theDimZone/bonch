package itsoftware.datdot.bonch;

import android.content.Intent;
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
            Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);

            switch (view.getId()) {
                case R.id.button:
                    intent.putExtra("buttonId", 0);
                    myTextView.setText("Вы использовали 1-й способ");
                    break;
                case R.id.button2:
                    intent.putExtra("buttonId", 1);
                    myTextView.setText("Вы использовали 2-й способ");
                    break;
                case R.id.button3:
                    intent.putExtra("buttonId", 2);
                    myTextView.setText("Вы использовали 3-й способ");
                    break;
                case R.id.button4:
                    intent.putExtra("buttonId", 4);
                    myTextView.setText("Вы использовали 4-й способ");
                    break;
            }
            startActivity(intent);
        }
    }


//    public final static String THIEF = "ru.alexanderklimov.sherlock.THIEF";
//    public void onRadioClick(View v) {
//        Intent answerIntent = new Intent();
//
//        switch (v.getId()) {
//            case R.id.button:
//                answerIntent.putExtra(THIEF, "Сраный пёсик");
//                break;
//            case R.id.button2:
//                answerIntent.putExtra(THIEF, "Ворона");
//                break;
//            case R.id.button4:
//                answerIntent.putExtra(THIEF, "Лошадь Пржевальского");
//                break;
//
//            default:
//                break;
//        }
//
//        setResult(RESULT_OK, answerIntent);
//        finish();
//    }
}
