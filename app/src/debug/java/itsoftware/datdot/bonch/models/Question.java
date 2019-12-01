package itsoftware.datdot.bonch.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Question {
    private String id;
    private String value;
    private int reward;
    private ArrayList<Answer> answers = new ArrayList<Answer>();

    public Question(String id, String value, int reward, ArrayList<Answer> answers) {
        this.id = id;
        this.value = value;
        this.reward = reward;
        this.answers = answers;
    }

    public Question() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return id.equals(question.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
