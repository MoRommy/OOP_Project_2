package data;

import common.Constants;
import data.input.*;
import data.output.AnnualChildren;
import data.output.ChildrenList;
import lombok.NonNull;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

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

        distributeAnnualPresents(inputData);
        List<ChildDisplay> childs = new ArrayList<>();
        for (Child c : inputData.getInitialData().getChildren()) {
            childs.add(new ChildDisplay(c));
        }
        children.addAnualChildList(new ChildrenList(childs));

        for (int i  = 1; i <= inputData.getNumberOfYears(); i++) {
            updateAnnualChanges(inputData, i - 1);
            distributeAnnualPresents(inputData);
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

        //update children
        for (ChildUpdate childUpdate:inputData.getAnnualChanges().get(year).getChildrenUpdates()) {
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

                }
            }
        }
    }

    private static void distributeAnnualPresents(@NonNull final InputData inputData) {
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
        for (Child child : inputData.getInitialData().getChildren()) {
            Double childBudget = budgetUnit * child.getAverageScore();
            child.setAssignedBudget(childBudget);
            Elf.assignGifts(child, santaGiftsList);
        }
    }
}
