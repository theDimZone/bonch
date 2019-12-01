package itsoftware.datdot.bonch.data.workers;

public class Achievement {
    private int id;
    private String value; // achievement 1,2,3 ....


    public Achievement(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Achievement that = (Achievement) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}