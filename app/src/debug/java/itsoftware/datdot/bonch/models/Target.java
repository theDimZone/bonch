package itsoftware.datdot.bonch.models;

import java.util.ArrayList;
import java.util.Map;
import com.google.firebase.firestore.GeoPoint;

public class Target {
    private String id;
    private String type; // "Archicture", "Bar', etc
    private int age;
    private int level; //1, 2, etc
    private GeoPoint geopoint;
    private ArrayList<GeoPoint> geopoint_cover = new ArrayList<GeoPoint>();
    private int reward;
    private ArrayList<Question> questions = new ArrayList<Question>();

    public Target(String id, String type, int age, int level, GeoPoint geopoint, ArrayList<GeoPoint> geopoint_cover, int reward, ArrayList<Question> questions) {
        this.id = id;
        this.type = type;
        this.age = age;
        this.level = level;
        this.geopoint = geopoint;
        this.geopoint_cover = geopoint_cover;
        this.reward = reward;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GeoPoint getGeopoint() {
        return geopoint;
    }

    public void setGeopoint(GeoPoint geopoint) {
        this.geopoint = geopoint;
    }

    public ArrayList<GeoPoint> getGeopoint_cover() {
        return geopoint_cover;
    }

    public void setGeopoint_cover(ArrayList<GeoPoint> geopoint_cover) {
        this.geopoint_cover = geopoint_cover;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Target target = (Target) o;

        return id.equals(target.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
