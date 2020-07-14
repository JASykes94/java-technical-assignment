package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;

public class BuyOneGetOneFreeDiscount implements Discount
{
    @Override
    public BigDecimal discountPrice(List<Item> items)
    {
       int numberOfItems = items.size();

       if (numberOfItems > 1)
       {
           // Size is an integer value so will round down when number of items is odd
           List<Item> discountedItems = items.subList(0, numberOfItems / 2);
           return doDiscount(discountedItems);
       }
       else
       {
           return BigDecimal.ZERO;
       }
    }

    private BigDecimal doDiscount(List<Item> discountedItems) {
        return discountedItems
                .stream()
                .map(Item::price)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
