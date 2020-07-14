package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of a BOGOF type discount, applied to all ItemByUnit objects.
 */
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
           // If there is one or zero items in the list, no discount is applied
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
