package itsoftware.datdot.bonch.models;

public class Answer {
    private String id;
    private String value;
    private boolean isCorrect;

    public Answer(String id, String value, boolean isCorrect) {
        this.id = id;
        this.value = value;
        this.isCorrect = isCorrect;
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

    public boolean getisCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
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
