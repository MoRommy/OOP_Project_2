package data.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.Constants;

import java.util.ArrayList;
import java.util.List;

public class Child {
    private final Integer id;
    private final String lastName;
    private final String firstName;
    private final String city;
    private Integer age;
    private List<String> giftsPreferences;
    private Double averageScore;
    private List<Double> niceScoreHistory = new ArrayList<>();
    private Double assignedBudget;
    private List<Gift> receivedGifts = new ArrayList<>();

    public Child(final Child c) {
        this.id = c.getId();
        this.lastName = c.getLastName();
        this.firstName = c.getFirstName();
        this.city = c.getCity();
        this.age = c.getAge();
        this.giftsPreferences = new ArrayList<>(c.getGiftsPreferences());
        this.averageScore = c.getAverageScore();
        this.niceScoreHistory = new ArrayList<>(c.getNiceScoreHistory());
        this.assignedBudget = c.getAssignedBudget();
        this.receivedGifts = new ArrayList<>(c.getReceivedGifts());
    }

    public Child(@JsonProperty("id") final Integer id,
                 @JsonProperty("lastName") final String lastName,
                 @JsonProperty("firstName") final String firstName,
                 @JsonProperty("city") final String city,
                 @JsonProperty("age") final Integer age,
                 @JsonProperty("giftsPreferences") final List<String> giftsPreferences,
                 @JsonProperty("niceScore") final Double niceScore) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.city = city;
        this.age = age;
        this.giftsPreferences = giftsPreferences;
        addNiceScore(niceScore);
    }

    /**
     *
     * @param niceScore nice score
     */
    public final void addNiceScore(final Double niceScore) {
        if (niceScore != null) {
            this.niceScoreHistory.add(niceScore);
        }
    }

    public final Integer getId() {
        return id;
    }

    public final String getLastName() {
        return lastName;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final String getCity() {
        return city;
    }

    public final Integer getAge() {
        return age;
    }

    public final void setAge(final Integer age) {
        this.age = age;
    }

    public final List<String> getGiftsPreferences() {
        return giftsPreferences;
    }

    public final void setGiftsPreferences(final List<String> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    private Double calculateAverageScore() {
        Double score = 0.0;
        for (Double s : getNiceScoreHistory()) {
            score += s;
        }
        return score / getNiceScoreHistory().size();
    }

    private Double calculateWeightAverageScore() {
        double score = 0.0;
        int i = 1, sum = 0;
        for (Double s : getNiceScoreHistory()) {
            score += s * i;
            sum += i;
            i++;
        }
        return score / sum;
    }

    /**
     *
     * @return safe getter for averageScore
     */
    public final Double getAverageScore() {
        if (averageScore != null) {
            return averageScore;
        }
        return 0.0;
    }

    /**
     * set average score for child
     */
    public final void setAverageScore() {
        if (getAge() < Constants.BABY_END_YEAR) {
            this.averageScore = Constants.MAX_SCORE;
        } else if (getAge() < Constants.KID_END_YEAR) {
            this.averageScore = calculateAverageScore();
        } else if (getAge() <= Constants.TEEN_END_YEAR) {
            this.averageScore = calculateWeightAverageScore();
        }
    }

    public final List<Double> getNiceScoreHistory() {
        return niceScoreHistory;
    }

    public final void setNiceScoreHistory(final List<Double> niceScoreHistory) {
        this.niceScoreHistory = niceScoreHistory;
    }

    public final Double getAssignedBudget() {
        return assignedBudget;
    }

    public final void setAssignedBudget(final Double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }

    public final List<Gift> getReceivedGifts() {
        return receivedGifts;
    }

    public final void setReceivedGifts(final List<Gift> receivedGifts) {
        this.receivedGifts = receivedGifts;
    }

    /**
     *
     */
    public final void clearReceivedGifts() {
        this.receivedGifts = new ArrayList<>();
    }
}
