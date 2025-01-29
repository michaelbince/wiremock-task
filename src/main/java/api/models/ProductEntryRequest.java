package api.models;

import lombok.Data;

@Data
public class ProductEntryRequest {
    private Product product;
    private int quantity;

    @Data
    public static class Product {
        private String code;
    }
}
