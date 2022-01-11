package data.input;

import java.util.ArrayList;
import java.util.List;

public class ChildDisplay {
    private final Integer id;
    private final String lastName;
    private final String firstName;
    private final String city;
    private final Integer age;
    private final List<String> giftsPreferences;
    private final Double averageScore;
    private final List<Double> niceScoreHistory;
    private final Double assignedBudget;
    private final List<GiftDisplay> receivedGifts;

    public ChildDisplay(final Child c) {
        this.id = c.getId();
        this.lastName = c.getLastName();
        this.firstName = c.getFirstName();
        this.city = c.getCity();
        this.age = c.getAge();
        this.giftsPreferences = new ArrayList<>(c.getGiftsPreferences());
        this.averageScore = c.getAverageScore();
        this.niceScoreHistory = new ArrayList<>(c.getNiceScoreHistory());
        this.assignedBudget = c.getAssignedBudget();
        this.receivedGifts = new ArrayList<>();
        for (Gift g : c.getReceivedGifts()) {
            this.receivedGifts.add(new GiftDisplay(g));
        }
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getCity() {
        return city;
    }

    public Integer getAge() {
        return age;
    }

    public List<String> getGiftsPreferences() {
        return giftsPreferences;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public List<Double> getNiceScoreHistory() {
        return niceScoreHistory;
    }

    public Double getAssignedBudget() {
        return assignedBudget;
    }

    public List<GiftDisplay> getReceivedGifts() {
        return receivedGifts;
    }
}
