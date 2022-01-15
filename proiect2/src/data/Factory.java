package data;

import common.Constants;
import data.input.Child;
import data.input.ChildDisplay;
import data.input.ChildUpdate;
import data.input.Elf;
import data.input.Gift;
import data.input.InputData;
import data.output.AnnualChildren;
import data.output.ChildrenList;
import lombok.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public final class Factory {
    private Factory() {
    }

    /**
     *
     * @param inputData input
     * @return the list of children after all years
     */
    public static AnnualChildren processData(final InputData inputData) {
        AnnualChildren children = new AnnualChildren();
        distributeAnnualPresents(inputData, -1);
        inputData.getInitialData().getChildren().sort(Comparator.comparing(Child::getId));
        List<ChildDisplay> childs = new ArrayList<>();
        for (Child c : inputData.getInitialData().getChildren()) {
            childs.add(new ChildDisplay(c));
        }
        children.addAnualChildList(new ChildrenList(childs));

        for (int i  = 1; i <= inputData.getNumberOfYears(); i++) {
            updateAnnualChanges(inputData, i - 1);
            distributeAnnualPresents(inputData, i - 1);
            inputData.getInitialData().getChildren().sort(Comparator.comparing(Child::getId));
            List<ChildDisplay> childs2 = new ArrayList<>();
            for (Child c : inputData.getInitialData().getChildren()) {
                childs2.add(new ChildDisplay(c));
            }
            children.addAnualChildList(new ChildrenList(childs2));
        }
        return children;
    }

    private static void updateAnnualChanges(final InputData inputData, final int year) {
        //set new age
        for (Child c : inputData.getInitialData().getChildren()) {
            c.setAge(c.getAge() + 1);
        }

        //set santa budget
        inputData.setSantaBudget(inputData.getAnnualChanges().get(year).getNewSantaBudget());

        //add new gifts
        for (Gift gift : inputData.getAnnualChanges().get(year).getNewGifts()) {
            inputData.getInitialData().getSantaGiftsList().add(gift);
        }

        //add new children
        for (Child child : inputData.getAnnualChanges().get(year).getNewChildren()) {
            if (child.getAge() <= Constants.TEEN_END_YEAR) {
                inputData.getInitialData().getChildren().add(child);
            }
        }

        updateChildren(inputData, year);
    }

    private static void updateChildren(final InputData inputData, final int year) {
        for (ChildUpdate childUpdate: inputData.getAnnualChanges().get(year).getChildrenUpdates()) {
            for (Child child : inputData.getInitialData().getChildren()) {
                if (child.getId().equals(childUpdate.getId())) {
                    //add nice score
                    child.addNiceScore(childUpdate.getNiceScore());

                    //add gifts preferences
                    Collections.reverse(childUpdate.getGiftsPreferences());
                    for (String giftPreference : childUpdate.getGiftsPreferences()) {
                        child.getGiftsPreferences().add(0, giftPreference);
                    }
                    child.setGiftsPreferences(new ArrayList<>(
                                new LinkedHashSet<>(child.getGiftsPreferences())));
                    child.setElf(childUpdate.getElf());
                }
            }
        }
    }

    private static void distributeAnnualPresents(@NonNull final InputData inputData,
                                                                            final Integer year) {
        Double scoreSum = 0.0;
        inputData.getInitialData().getChildren().removeIf(
                child -> child.getAge() > Constants.TEEN_END_YEAR);
        for (Child child : inputData.getInitialData().getChildren()) {
            child.setAverageScore();
            scoreSum += child.getAverageScore();
            child.clearReceivedGifts();
        }
        Double budgetUnit = inputData.getSantaBudget() / scoreSum;
        List<Gift> santaGiftsList = inputData.getInitialData().getSantaGiftsList();
        List<Child> childs = getChildListByStrategy(inputData, year);
        for (Child child : childs) {
            Double childBudget = budgetUnit * child.getAverageScore();
            child.setAssignedBudget(childBudget);
            Elf.assignGifts(child, santaGiftsList);
        }
    }

    private static List<Child> getChildListByStrategy(final InputData inputData,
                                                                            final Integer year) {
        List<Child> childList = inputData.getInitialData().getChildren();
        if (year == -1) {
            return childList;
        }
        String strategy = inputData.getAnnualChanges().get(year).getStrategy();
        switch (strategy) {
            case "niceScore" :
                childList.sort(Comparator.comparing(Child::getAverageScore).reversed());
                break;
            case "niceScoreCity" : return sortByNiceScoreCity(childList);
            default : return childList;
        }
        return childList;
    }

    private static List<Child> sortByNiceScoreCity(final List<Child> childList) {
        HashMap<String, Double> cityAverageScore = new HashMap<>();
        HashMap<String, Integer> numberOfCitizens = new HashMap<>();

        for (Child c : childList) {
            if (cityAverageScore.containsKey(c.getCity())) {
                cityAverageScore.replace(c.getCity(),
                                        cityAverageScore.get(c.getCity()) + c.getAverageScore());
                numberOfCitizens.replace(c.getCity(), numberOfCitizens.get(c.getCity()) + 1);
            } else {
                cityAverageScore.put(c.getCity(), c.getAverageScore());
                numberOfCitizens.put(c.getCity(), 1);
            }
        }

        for (Map.Entry<String, Double> mapElement : cityAverageScore.entrySet()) {
            String city = mapElement.getKey();
            Double score = mapElement.getValue();
            cityAverageScore.replace(city, score / numberOfCitizens.get(city));
        }

        childList.sort((o1, o2) -> {
            if (cityAverageScore.get(o1.getCity()) > cityAverageScore.get(o2.getCity())) {
                return -1;
            } else if (cityAverageScore.get(o1.getCity()) < cityAverageScore.get(o2.getCity())) {
                return 1;
            } else {
                return o1.getCity().compareTo(o2.getCity());
            }
        });
        return childList;
    }
}
