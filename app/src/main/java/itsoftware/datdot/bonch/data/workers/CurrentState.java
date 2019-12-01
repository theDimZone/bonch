package itsoftware.datdot.bonch.data.workers;

public class CurrentState {
    private static CurrentState instance;
    private static User user;

    private CurrentState() {}

    public static CurrentState getInstance() {
        if (instance == null) {
            instance = new CurrentState();
        }
        return instance;
    }

    public static User getUser() {
        if(user == null) return new User();
        return user;
    }

    public static void setUser(User newuser) {
        user = newuser;
    }
}