package kitchenpos.domain.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class MenuProducts {
    private static final String NOT_EXIST_MENU_PRODUCTS = "MenuProducts 이 존재하지 않습니다.";

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected MenuProducts() {}

    private MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public static MenuProducts createEmpty() {
        return new MenuProducts(new ArrayList<>());
    }

    public static MenuProducts from(List<MenuProduct> menuProducts) {
        validateExistMenuProducts(menuProducts);
        return new MenuProducts(menuProducts);
    }

    public List<MenuProduct> getValues() {
        return Collections.unmodifiableList(menuProducts);
    }

    private static void validateExistMenuProducts(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts)) {
            throw new IllegalArgumentException(NOT_EXIST_MENU_PRODUCTS);
        }
    }
}