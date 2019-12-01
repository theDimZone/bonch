package itsoftware.datdot.bonch;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import itsoftware.datdot.bonch.data.workers.Question;
import itsoftware.datdot.bonch.data.workers.Target;
import itsoftware.datdot.bonch.data.workers.User;


public class QuestionsActivity extends AppCompatActivity {
    //public ArrayList<Question> questions = new ArrayList<Question>();
    //public String idtarget;
    //public Question momentQuestion = questions.get(i);
    //public ArrayList<Answer> momentQuestion;
    public FirebaseFirestore db;
    public ArrayList<Question> questions;
    public int current = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        db = FirebaseFirestore.getInstance();

        String target_id = getIntent().getStringExtra("target");

        db.collection("targets")
                .whereEqualTo("id", target_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Target target = new Target();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                target = document.toObject(Target.class);
                            }

                            setQuestions(target.getQuestions());
                        }
                    }
                });
    }

    public void setQuestions(ArrayList<Question> q) {
        this.questions = q;
    }

    public void checkDesire() {
        // спросить хочет или нет
    }

    public void showCurrentQuestion() {

    }


}
