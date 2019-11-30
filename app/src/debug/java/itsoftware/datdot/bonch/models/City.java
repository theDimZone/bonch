package itsoftware.datdot.bonch.models;

import java.util.ArrayList;
import java.util.HashMap;

public class City {
    private String id;
    private String name;
    private ArrayList<Target> targets = new ArrayList<Target>();

    public ArrayList<Target> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Target> targets) {
        this.targets = targets;
    }

    public City(String id, String name, ArrayList<Target> targets) {
        this.id = id;
        this.name = name;
        this.targets = targets;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (!id.equals(city.id)) return false;
        return name != null ? name.equals(city.name) : city.name == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
