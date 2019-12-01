package itsoftware.datdot.bonch.data.workers;

import java.util.ArrayList;


public class User {
    private String nickname;
    private String id;
    private int experience;
    private int age;
    private int level;

    private  ArrayList<String> achievements = new ArrayList<String>();
    private ArrayList<String> passed_questions = new ArrayList<String>();
    private ArrayList<String> visited_target = new ArrayList<String>();

    public User( String nickname, String id,
                 int experience, int age, int level, ArrayList<String> achievements,
                 ArrayList<String> passed_questions, ArrayList<String> visited_target) {
        this.nickname = nickname;
        this.id = id;
        this.experience = experience;
        this.age = age;
        this.level = level;
        this.achievements = achievements;
        this.passed_questions = passed_questions;
        this.visited_target = visited_target;

    }

    public ArrayList<String> getAchievements() {
        return achievements;
    }

    public void setAchievements(ArrayList<String> achievements) {
        this.achievements = achievements;
    }

    public ArrayList<String> getPassed_questions() {
        return passed_questions;
    }

    public void setPassed_questions(ArrayList<String> passed_questions) {
        this.passed_questions = passed_questions;
    }

    public ArrayList<String> getVisited_target() {
        return visited_target;
    }

    public void setVisited_target(ArrayList<String> visited_target) {
        this.visited_target = visited_target;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }
    public String getNickname() {
        return nickname;
    }
    public int getExperience() {
        return experience;
    }
    public int getAge() {
        return age;
    }
    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}