package gilts;

public class MallonGiltPricingEngine implements GiltPricingEngine {
    private double twoYear, fiveYear, tenYear, twentyYear;

    public MallonGiltPricingEngine(double twoYear, double fiveYear, double tenYear, double twentyYear) {
        this.twoYear = twoYear;
        this.fiveYear = fiveYear;
        this.tenYear = tenYear;
        this.twentyYear = twentyYear;
    }

    @Override
    public double getPrice(Gilt g) {
        double yield = getYield(g) / 100;
        double price = ((2 * g.getYearsRemaining() * g.getCoupon()) - (g.getYearsRemaining() * g.getPrincipal() * yield) + (2 * g.getPrincipal())) / (g.getYearsRemaining() * yield + 2);
        return price;
    }

    public double getYield(Gilt g) {
//        double yieldToMaturity = (g.getCoupon() + (g.getPrincipal() - Price) / g.getYearsRemaining()) / ((Price + g.getPrincipal()) / 2);
//        return yieldToMaturity;

        if (g.getYearsRemaining() <= 2) {
            return twoYear;
        } else if (g.getYearsRemaining() <= 5) {
            return fiveYear;
        } else if (g.getYearsRemaining() <= 10) {
            return tenYear;
        } else {
            return twentyYear;
        }
    }
}
