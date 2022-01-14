package data.input;

import java.util.ArrayList;
import java.util.List;

public class Elf {

    public static void assignGifts(Child child, List<Gift> santaGifts) {
        child.setAssignedBudget(getElfBudget(child));
        assignSantaGifts(child, santaGifts);
        assignElfGifts(child, santaGifts, child.getElf());
    }

    private static void assignSantaGifts(Child child, List<Gift> santaGifts) {
        Double childBudget = child.getAssignedBudget();
        for (String giftCategory : child.getGiftsPreferences()) {
            List<Gift> gifts = getAllGiftsFromCategory(santaGifts, giftCategory);
            sortGiftsByPriceAscending(gifts);
            if (gifts.size() > 0) {
                if (gifts.get(0).getPrice() <= childBudget) {
                    child.giveGift(gifts.get(0));
                    childBudget -= gifts.get(0).getPrice();
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

    private static List<Gift> getAllGiftsFromCategory(final List<Gift> santaGiftsList,
                                                      final String giftCategory) {
        List<Gift> allGiftsFromCategory = new ArrayList<>();
        for (Gift g : santaGiftsList) {
            if (g.getCategory().equals(giftCategory) && g.getQuantity() > 0) {
                allGiftsFromCategory.add(g);
            }
        }
        return allGiftsFromCategory;
    }

    private static void assignElfGifts(Child child, List<Gift> santaGifts, String elf) {
        switch (elf) {
            case "yellow" -> {
                if (child.getReceivedGifts().size() == 0) {
                    assignFavouriteGift(child, santaGifts);
                }
            }
        }
    }

    private static void assignFavouriteGift(Child child, List<Gift> santaGifts) {
        String favouriteCategory = child.getGiftsPreferences().get(0);
        Double lowestPrice = 9999999.9;
        Gift lowestPriceGift = null;
        for (Gift gift: santaGifts) {
            if (gift.getCategory().equals(favouriteCategory)) {
                if (gift.getPrice() < lowestPrice) {
                    lowestPrice = gift.getPrice();
                    lowestPriceGift = gift;
                }
            }
        }
        if (lowestPriceGift != null) {
            child.giveGift(lowestPriceGift);
        }
        for (Gift gift : santaGifts) {
            if (gift.getPrice() < lowestPrice) {
                lowestPrice = gift.getPrice();
                lowestPriceGift = gift;
            }
        }
        child.giveGift(lowestPriceGift);
    }

    private static Double getElfBudget(Child child) {
        String elf = child.getElf();
        Double assignedBudget = child.getAssignedBudget();
        switch (elf) {
            case "black" -> {
                return assignedBudget - (30 / 100) * assignedBudget;
            }
            case "pink" -> {
                return assignedBudget + (30 / 100) * assignedBudget;
            }
        }
        return assignedBudget;
    }
}
