package itsoftware.datdot.bonch.models;

public class Answer {
    private String id;
    private String value;
    private boolean is_correct;

    public Answer(String id, String value, boolean is_correct) {
        this.id = id;
        this.value = value;
        this.is_correct = is_correct;
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

    public boolean isIs_correct() {
        return is_correct;
    }

    public void setIs_correct(boolean is_correct) {
        this.is_correct = is_correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return id.equals(answer.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
