package kitchenpos.domain.vo;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

	@DisplayName("가격은 음수가 될 수 없다.")
	@Test
	void negativePriceTest() {
		// given
		// when
		// than
		assertThatThrownBy(() -> Price.wonOf(BigDecimal.valueOf(-1)))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("가격은 음수가 될 수 없습니다.");
	}

	@DisplayName("가격을 n 배 할 수있다.")
	@Test
	void timesPriceTest() {
		// given
		Price 천원 = Price.wonOf(1000);

		// when
		Price 만원 = 천원.times(10);

		// than
		assertThat(만원).isEqualTo(Price.wonOf(10000));
	}

	@DisplayName("가격끼리 더 할 수 있다.")
	@Test
	void plusPriceTest() {
		// given
		Price 천원 = Price.wonOf(1000);
		Price 만원 = Price.wonOf(10000);

		// when
		Price 만천원 = 만원.plus(천원);

		// than
		assertThat(만천원).isEqualTo(Price.wonOf(11000));
	}

	@DisplayName("가격과 수량을 곱할 수 있다.")
	@Test
	void priceXQuantityTest() {
		// given
		Price 천원 = Price.wonOf(1000);
		Quantity 두개 = Quantity.of(2);

		// when
		Price 이천원 = 천원.times(두개);

		// then
		assertThat(이천원).isEqualTo(Price.wonOf(2000));
	}

}