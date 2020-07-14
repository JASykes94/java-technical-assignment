package kata.supermarket;

import com.sun.tools.javac.jvm.Items;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Basket {
    private final List<Item> items;

    public Basket() {
        this.items = new ArrayList<>();
    }

    public void add(final Item item) {
        this.items.add(item);
    }

    List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal total() {
        return new TotalCalculator().calculate();
    }

    private class TotalCalculator {
        private final List<Item> items;

        TotalCalculator() {
            this.items = items();
        }
        OneKilogramHalfPriceDiscount oneKilogramHalfPriceDiscount = new OneKilogramHalfPriceDiscount();
        BuyOneGetOneFreeDiscount buyOneGetOneFreeDiscount = new BuyOneGetOneFreeDiscount();

        private BigDecimal subtotal() {
            return items.stream().map(Item::price)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        private BigDecimal discounts() {
            List<Item> unitItems = new ArrayList<>();
            List<Item> weightItems = new ArrayList<>();

            for (Item item : items) {
                if (item instanceof ItemByUnit) {
                    unitItems.add(item);
                } else {
                    weightItems.add(item);
                }
            }

            BigDecimal unitDiscount = buyOneGetOneFreeDiscount.discountPrice(unitItems);
            BigDecimal weightDiscount = oneKilogramHalfPriceDiscount.discountPrice(weightItems);

            return unitDiscount.add(weightDiscount);
        }

        private BigDecimal calculate() {
            return subtotal().subtract(discounts());
        }
    }
}
