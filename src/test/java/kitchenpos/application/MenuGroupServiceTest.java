package kitchenpos.application;

import kitchenpos.domain.MenuGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MenuGroupServiceTest {
    @Autowired
    MenuGroupService menuGroupService;

    @Test
    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    void create() {
        final MenuGroup menuGroup = getMenuGroup();

        final MenuGroup savedMenuGroup = menuGroupService.create(menuGroup);

        assertThat(savedMenuGroup.getName()).isEqualTo(menuGroup.getName());
    }

    @Test
    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    void list() {
        menuGroupService.create(getMenuGroup());
        menuGroupService.create(getMenuGroup());

        final List<MenuGroup> list = menuGroupService.list();

        assertThat(list.size()).isGreaterThanOrEqualTo(2);
    }

    private MenuGroup getMenuGroup() {
        final MenuGroup menuGroup = new MenuGroup();
        menuGroup.setId(1L);
        menuGroup.setName("추천메뉴");
        return menuGroup;
    }
}
