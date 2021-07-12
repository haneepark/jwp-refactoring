package kitchenpos.product.dto;

public class CreateProductRequest {
    private String name;
    private Long price;

    protected CreateProductRequest() { }

    public CreateProductRequest(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }
}