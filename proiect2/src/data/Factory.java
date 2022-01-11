package data;

import common.Constants;
import data.input.Child;
import data.input.ChildUpdate;
import data.input.Gift;
import data.input.InputData;
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
        List<Child> childs = new ArrayList<>();
        for (Child c : inputData.getInitialData().getChildren()) {
            childs.add(new Child(c));
        }
        children.addAnualChildList(new ChildrenList(childs));

        for (int i  = 1; i <= inputData.getNumberOfYears(); i++) {
            updateAnnualChanges(inputData, i - 1);
            distributeAnnualPresents(inputData);
            List<Child> childs2 = new ArrayList<>();
            for (Child c : inputData.getInitialData().getChildren()) {
                childs2.add(new Child(c));
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
        for (Child child : inputData.getInitialData().getChildren()) {
            Double childBudget = budgetUnit * child.getAverageScore();
            child.setAssignedBudget(childBudget);
            for (String giftCategory : child.getGiftsPreferences()) {
                List<Gift> allGiftsFromCategory = getAllGiftsFromCategory(inputData, giftCategory);
                sortGiftsByPriceAscending(allGiftsFromCategory);
                if (allGiftsFromCategory.size() > 0) {
                    if (allGiftsFromCategory.get(0).getPrice() <= childBudget) {
                        child.getReceivedGifts().add(allGiftsFromCategory.get(0));
                        childBudget -= allGiftsFromCategory.get(0).getPrice();
                    }
                }
            }
        }
    }

    private static void sortGiftsByPriceAscending(final List<Gift> allGiftsFromCategory) {
        allGiftsFromCategory.sort((o1, o2) -> {
            if (o1.getPrice() > o2.getPrice()) {
                return 1;
            } else if (o1.getPrice() < o2.getPrice()) {
                return -1;
            }
            return 0;
        });
    }

    private static List<Gift> getAllGiftsFromCategory(final InputData inputData,
                                                      final String giftCategory) {
        List<Gift> allGiftsFromCategory = new ArrayList<>();
        for (Gift g : inputData.getInitialData().getSantaGiftsList()) {
            if (g.getCategory().equals(giftCategory)) {
                allGiftsFromCategory.add(g);
            }
        }
        return allGiftsFromCategory;
    }
}
