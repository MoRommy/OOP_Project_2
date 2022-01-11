package data.input;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Gift {
    private final String productName;
    private final Double price;
    private final String category;
    private final Integer quantity;

    public Gift(@JsonProperty("productName") final String productName,
                @JsonProperty("price") final Double price,
                @JsonProperty("category") final String category,
                @JsonProperty("quantity") final Integer quantity) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
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
