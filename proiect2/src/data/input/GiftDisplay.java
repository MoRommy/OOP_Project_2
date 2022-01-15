package data.input;

public class GiftDisplay {
    private final String productName;
    private final Double price;
    private final String category;

    public GiftDisplay(final Gift gift) {
        this.productName = gift.getProductName();
        this.price = gift.getPrice();
        this.category = gift.getCategory();
    }

    public final String getProductName() {
        return productName;
    }

    public final Double getPrice() {
        return price;
    }

    public final String getCategory() {
        return category;
    }
}
