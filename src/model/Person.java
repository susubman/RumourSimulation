package src.model;

public class Person {

    public enum State {
        IGNORANT, SPREADER, STIFLER
    }

    private State state = State.IGNORANT;
    private final int groupId;
    private final double gullibility;
    private boolean factChecker = false;

    public Person(int groupId, double gullibility) {
        this.groupId = groupId;
        this.gullibility = gullibility;
    }

    // Getters
    public State getState() {
        return state;
    }

    public int getGroupId() {
        return groupId;
    }

    public double getGullibility() {
        return gullibility;
    }

    public boolean isFactChecker() {
        return factChecker;
    }

    // Setters
    public void setState(State state) {
        this.state = state;
    }

    public void setFactChecker(boolean factChecker) {
        this.factChecker = factChecker;
    }

    // Utility
    public boolean isIgnorant() {
        return state == State.IGNORANT;
    }

    public boolean isSpreader() {
        return state == State.SPREADER;
    }

    public boolean isStifler() {
        return state == State.STIFLER;
    }
}
