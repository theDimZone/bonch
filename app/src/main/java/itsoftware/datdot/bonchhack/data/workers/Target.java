package itsoftware.datdot.bonchhack.data.workers;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class Target {
    private String id;
    private String name;
    private int age;
    private boolean boomer;
    private boolean zoomer;
    private GeoPoint location;
    private ArrayList<GeoPoint> pointsArea;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isBoomer() {
        return boomer;
    }

    public void setBoomer(boolean boomer) {
        this.boomer = boomer;
    }

    public boolean isZoomer() {
        return zoomer;
    }

    public void setZoomer(boolean zoomer) {
        this.zoomer = zoomer;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public ArrayList<GeoPoint> getPointsArea() {
        return pointsArea;
    }

    public void setPointsArea(ArrayList<GeoPoint> pointsArea) {
        this.pointsArea = pointsArea;
    }
}
