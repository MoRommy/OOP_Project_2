package data.input;

import common.Constants;

import java.util.ArrayList;
import java.util.List;

public final class Elf {
    private Elf() {

    }

    /**
     *
     * @param child c
     * @param santaGifts s
     */
    public static void assignGifts(final Child child, final List<Gift> santaGifts) {
        child.setAssignedBudget(getElfBudget(child));
        assignSantaGifts(child, santaGifts);
        assignElfGifts(child, santaGifts, child.getElf());
    }

    private static void assignSantaGifts(final Child child, final List<Gift> santaGifts) {
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

    private static void assignElfGifts(final Child child, final List<Gift> santaGifts,
                                                                            final String elf) {
        if ("yellow".equals(elf)) {
            if (child.getReceivedGifts().size() == 0) {
                assignFavouriteGift(child, santaGifts);
            }
        }
    }

    private static void assignFavouriteGift(final Child child, final List<Gift> santaGifts) {
        String favouriteCategory = child.getGiftsPreferences().get(0);
        Double lowestPrice = Constants.INFINITE_VALUE;
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

    private static Double getElfBudget(final Child child) {
        String elf = child.getElf();
        Double assignedBudget = child.getAssignedBudget();
        switch (elf) {
            case "black" -> {
                assignedBudget +=
                        (Constants.BLACK_ELF_VALUE * assignedBudget) / Constants.ONE_HUNDRED;
            }
            case "pink" -> {
                assignedBudget +=
                        (Constants.PINK_ELF_VALUE * assignedBudget) / Constants.ONE_HUNDRED;
            }
            default -> {
            }
        }
        return assignedBudget;
    }
}
