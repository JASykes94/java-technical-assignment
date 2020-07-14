package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;

public class OneKilogramHalfPriceDiscount implements Discount
{
    @Override
    public BigDecimal discountPrice(List<Item> items)
    {
        BigDecimal weight = new BigDecimal(0);

        for (Item item : items)
        {
            ItemByWeight itemByWeight = (ItemByWeight) item;
            weight = weight.add(itemByWeight.getWeightInKilos());
        }

        if (weight.stripTrailingZeros().scale() <= 0)
        {
            // This means there is a whole number of kilograms, so the discount is just half price
            BigDecimal total = getTotal(items);

            return total.divide(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            BigDecimal[] splitWeight = weight.divideAndRemainder(BigDecimal.ONE);
            BigDecimal wholeKilograms = splitWeight[0];

            BigDecimal pricePerKilogram = getTotal(items).divide(weight, BigDecimal.ROUND_HALF_UP);

            return pricePerKilogram.multiply(wholeKilograms).divide(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    private BigDecimal getTotal(List<Item> items) {
        return items
                .stream()
                .map(Item::price)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
