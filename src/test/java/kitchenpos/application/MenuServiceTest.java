package kitchenpos.application;

import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
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
public class MenuServiceTest {

    @Autowired
    MenuService menuService;

    @Autowired
    MenuGroupService menuGroupService;

    @Autowired
    ProductService productService;

    @Test
    @DisplayName("메뉴를 등록할 수 있다")
    void create_success() {
        final Menu menu = getMenu();

        final Menu savedMenu = menuService.create(menu);

        assertThat(savedMenu.getName()).isEqualTo(menu.getName());
    }

    @Test
    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다")
    void create_fail_price() {
        final Menu menu = getMenu();
        menu.setPrice(new BigDecimal(-100));

        assertThatThrownBy(() -> menuService.create(menu))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 그룹이 올바르지 않으면 등록할 수 없다")
    void create_fail_menuGroup() {
        final Menu menu = getMenu();
        menu.setMenuGroupId(9999999L);

        assertThatThrownBy(() -> menuService.create(menu))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 상품 목록이 올바르지 않으면 등록할 수 없다")
    void create_fail_menuProduct() {
        final Menu menu = getMenu();
        menu.getMenuProducts().get(0).setProductId(9999999L);

        assertThatThrownBy(() -> menuService.create(menu))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴의 목록을 조회할 수 있다")
    void list() {
        menuService.create(getMenu());
        menuService.create(getMenu());

        final List<Menu> list = menuService.list();

        assertThat(list.size()).isGreaterThanOrEqualTo(2);
    }

    private Menu getMenu() {
        final MenuGroup menuGroup = getSavedMenuGroup();
        final Product product = getSavedProduct();

        return Menu.ofSingleProduct(menuGroup, product);
    }

    private MenuGroup getSavedMenuGroup() {
        final MenuGroup menuGroup = new MenuGroup();
        menuGroup.setName("추천메뉴");
        return menuGroupService.create(menuGroup);
    }

    private Product getSavedProduct() {
        final Product product = new Product();
        product.setName("강정치킨");
        product.setPrice(new BigDecimal(17_000));
        return productService.create(product);
    }
}
