package kata.supermarket;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class BuyOneGetOneFreeDiscountTest
{
    private BuyOneGetOneFreeDiscount discounter = new BuyOneGetOneFreeDiscount();
    private Product canOfCoke = new Product(new BigDecimal(0.99));
    private Item canOfCokeItem = new ItemByUnit(canOfCoke);

    @Test
    public void shouldApplyNoDiscountToOneItem() {
        List<Item> items = asList(canOfCokeItem);
        BigDecimal discount = discounter.discountPrice(items);
        assertEquals(BigDecimal.ZERO, discount);
    }

    @Test
    public void shouldApplyDiscountToTwoItems() {
        List<Item> items = asList(canOfCokeItem, canOfCokeItem);
        BigDecimal discount = discounter.discountPrice(items);
        assertEquals(new BigDecimal(0.99).setScale(2, BigDecimal.ROUND_HALF_UP), discount);
    }

    @Test
    public void shouldApplyDiscountToThreeItems() {
        List<Item> items = asList(canOfCokeItem, canOfCokeItem, canOfCokeItem);
        BigDecimal discount = discounter.discountPrice(items);
        assertEquals(new BigDecimal(0.99).setScale(2, BigDecimal.ROUND_HALF_UP), discount);
    }
}
