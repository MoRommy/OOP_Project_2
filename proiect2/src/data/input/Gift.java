package data.input;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Gift {
    private final String productName;
    private final Double price;
    private final String category;

    public Gift(@JsonProperty("productName") final String productName,
                @JsonProperty("price") final Double price,
                @JsonProperty("category") final String category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
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
