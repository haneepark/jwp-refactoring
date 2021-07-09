package kitchenpos.repository;

import kitchenpos.domain.menu.MenuProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuProductRepository extends JpaRepository<MenuProduct, Long> {
    List<MenuProduct> findAllByMenu_Id(Long menuId);
}