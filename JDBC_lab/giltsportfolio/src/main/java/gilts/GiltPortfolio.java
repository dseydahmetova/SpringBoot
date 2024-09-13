package gilts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GiltPortfolio {
    public List<Gilt> getPortfolio() {
        return portfolio;
    }

    public double getBalance() {
        return balance;
    }

    private GiltPricingEngine pricingEngine;
    private List<Gilt> portfolio;
    private double balance;

    public GiltPortfolio(GiltPricingEngine pricingEngine, List<Gilt> portfolio, double balance) {
        this.pricingEngine = pricingEngine;
        this.portfolio = portfolio;
        this.balance = balance;
    }

    public GiltPortfolio(GiltPricingEngine pricingEngine, double balance) {
        this(pricingEngine, new ArrayList<>(), balance);
    }

    public void buyGilt(Gilt g) {
        double price = pricingEngine.getPrice(g);
        if (price > balance) {
            throw new CantAffordGiltException();
        } else {
            portfolio.add(g);
            balance -= price;
        }
    }

    public void sellGilt(Gilt g) {
        double price = pricingEngine.getPrice(g);
        if (portfolio.contains(g)) {
            portfolio.remove(g);
            balance += price;
        } else {
            System.out.println("Gilt is not found");
        }
    }

    public void tick() {
        for (int i = portfolio.size() - 1; i >= 0; i--) {
            Gilt g = portfolio.get(i);
            this.balance += g.tick();
            if (g.expired()) {
                portfolio.remove(i);
            }
        }
    }
}
