package itsoftware.datdot.bonch;


import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import itsoftware.datdot.bonch.models.Answer;
import itsoftware.datdot.bonch.models.Question;

public class QuestionsActivity extends AppCompatActivity {
    public int i = 0, o = 1;
    public ArrayList<Question> questions = new ArrayList<Question>();
    public String idtarget;
    //public Question momentQuestion = questions.get(i);
    public ArrayList<Answer> momentQuestion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        CheckBox checkBox = findViewById(R.id.checkBox_answer);

        OnCheckedChangeListener listener = new OnCheckedChangeListener();
        checkBox.setOnCheckedChangeListener(listener);

        // load
        //questions = new ArrayList<Question>();
        ArrayList<Answer> answers = new ArrayList<Answer>();
        Answer answer = new Answer("test","tezt",true);
        Answer answer1 = new Answer("hi","test",true);
        answers.add(answer);
        answers.add(answer1);
        Question question = new Question("test", "tezt", 50, answers);
        Question question1 = new Question("test","call",60,answers);
        questions.add(question);
        questions.add(question1);
        momentQuestion = questions.get(0).getAnswers();
    }




    private class OnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            for(i = 0; i < questions.size(); i++) {
                if (!momentQuestion.get(o).getisCorrect()) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.incorrect), Toast.LENGTH_SHORT).show();
                    //i++;
                    o++;
                    momentQuestion = questions.get(o).getAnswers();
                    compoundButton.setChecked(!b);

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.correct), Toast.LENGTH_SHORT).show();
                    TextView textView = findViewById(R.id.textView_question);
                    textView.setText(questions.get(i).getValue());
                    // i++;
                    compoundButton.setChecked(!b);
                }
            }


        }
    }

}
