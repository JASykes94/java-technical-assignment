package kata.supermarket;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class OneKilogramHalfPriceDiscountTest
{
    private OneKilogramHalfPriceDiscount discounter = new OneKilogramHalfPriceDiscount();
    private WeighedProduct kiloOfCarrots = new WeighedProduct(new BigDecimal(1.49).setScale(2, BigDecimal.ROUND_HALF_UP));
    private Item kiloOfCarrotsItem = new ItemByWeight(kiloOfCarrots, new BigDecimal(1));
    private Item halfKiloOfCarrotsItem = new ItemByWeight(kiloOfCarrots, new BigDecimal(0.5));
    private Item kiloAndHalfOfCarrotsItem = new ItemByWeight(kiloOfCarrots, new BigDecimal(1.5));

    @Test
    public void shouldApplyNoDiscountToEmptyList() {
        List<Item> items = emptyList();
        BigDecimal discount = discounter.discountPrice(items);
        assertEquals(BigDecimal.ZERO.setScale(2), discount);
    }

    @Test
    public void shouldApplyDiscountToOneItemTotallingKiloOfCarrots() {
        List<Item> items = singletonList(kiloOfCarrotsItem);
        BigDecimal discount = discounter.discountPrice(items);
        assertEquals(new BigDecimal(0.75), discount);
    }

    @Test
    public void shouldApplyDiscountToMultipleItemsTotallingKiloOfCarrots() {
        List<Item> items = asList(halfKiloOfCarrotsItem, halfKiloOfCarrotsItem);
        BigDecimal discount = discounter.discountPrice(items);
        assertEquals(new BigDecimal(0.75), discount);
    }

    @Test
    public void shouldApplyDiscountToOneItemWeightingOneAndHalfKilos() {
        List<Item> items = singletonList(kiloAndHalfOfCarrotsItem);
        BigDecimal discount = discounter.discountPrice(items);
        assertEquals(new BigDecimal(0.75), discount);
    }

    @Test
    public void shouldApplyDiscountToMultipleItemsTotallingKiloAndHalfOfCarrots()
    {
        List<Item> items = asList(halfKiloOfCarrotsItem, halfKiloOfCarrotsItem);
        BigDecimal discount = discounter.discountPrice(items);
        assertEquals(new BigDecimal(0.75), discount);
    }
}
