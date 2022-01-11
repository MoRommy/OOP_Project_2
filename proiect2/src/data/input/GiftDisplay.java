package data.input;

public class GiftDisplay {
    private final String productName;
    private final Double price;
    private final String category;

    public GiftDisplay(Gift gift) {
        this.productName = gift.getProductName();
        this.price = gift.getPrice();
        this.category = gift.getCategory();
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
