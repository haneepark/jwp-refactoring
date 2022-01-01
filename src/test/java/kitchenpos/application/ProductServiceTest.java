package kitchenpos.application;

import kitchenpos.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품을 등록할 수 있다.")
    void create_success() {
        final Product product = getProduct();

        final Product savedProduct = productService.create(product);

        assertThat(savedProduct.getName()).isEqualTo(product.getName());
    }

    @Test
    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    void create_fail() {
        final Product product = getProduct();
        product.setPrice(new BigDecimal(-100));

        assertThatThrownBy(() -> productService.create(product))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품의 목록을 조회할 수 있다.")
    void list() {
        productService.create(getProduct());
        productService.create(getProduct());

        final List<Product> list = productService.list();

        assertThat(list.size()).isGreaterThanOrEqualTo(2);
    }

    private Product getProduct() {
        final Product product = new Product();
        product.setId(1L);
        product.setName("강정치킨");
        product.setPrice(new BigDecimal(17_000));
        return product;
    }
}
