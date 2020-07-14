package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;

public class OneKilogramHalfPriceDiscount implements Discount
{
    @Override
    public BigDecimal discountPrice(List<Item> items)
    {
        BigDecimal weight = new BigDecimal(0);

        // Get the total weight of all items in the list (assuming all items are the same)
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
            // Find the whole number of kilograms
            BigDecimal[] splitWeight = weight.divideAndRemainder(BigDecimal.ONE);
            BigDecimal wholeKilograms = splitWeight[0];

            // Calculate the price per kilogram from total price
            BigDecimal pricePerKilogram = getTotal(items).divide(weight, BigDecimal.ROUND_HALF_UP);

            // Calculate the price for the whole kilograms in the list and apply discount
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
