package itsoftware.datdot.bonch.models;

import java.util.ArrayList;
import java.util.Map;
import com.google.firebase.firestore.GeoPoint;

public class Target {
    private String id;
    private String type; // "Archicture", "Bar', etc
    private int age;
    private int level; //1, 2, etc
    private int reward;
    private GeoPoint geopoint;
    private ArrayList<Question> questions;
    private ArrayList<GeoPoint> geopointCover;

    public Target(String id, String type, int age, int level, GeoPoint geopoint,
                  ArrayList<GeoPoint> geopointCover, int reward, ArrayList<Question> questions) {
        this.id = id;
        this.type = type;
        this.age = age;
        this.level = level;
        this.geopoint = geopoint;
        this.geopointCover = geopointCover;
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
        return geopointCover;
    }

    public void setGeopoint_cover(ArrayList<GeoPoint> geopointCover) {
        this.geopointCover = geopointCover;
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
