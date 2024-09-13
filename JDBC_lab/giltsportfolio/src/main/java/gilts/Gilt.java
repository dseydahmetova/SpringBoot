package gilts;

public class Gilt {
    private double coupon; // Interest (absolute: i.e. how much are they being paid?)
    private double principal; // Face value
    private int yearsRemaining; // Years remaining on the bond
    private int id;

    public double getCouponPercent() {
        return coupon / principal;
    }

    public double getCoupon() {
        return coupon;
    }

    public double getPrincipal() {
        return principal;
    }

    public int getYearsRemaining() {
        return yearsRemaining;
    }


    // Private constructor with create method (private keyword was removed, because test is not working)
    private Gilt(double coupon, double principal, int yearsRemaining) {
        this.coupon = coupon;
        this.principal = principal;
        this.yearsRemaining = yearsRemaining;
    }

    public static Gilt create(double coupon, double principal, int yearsRemaining) {
        // check input parameters
        if (coupon < 0 || principal < 0.01 || yearsRemaining < 0) {
            throw new IllegalArgumentException("Invalid parameters: coupon, principal, and yearsRemaining must be non-negative.");
        }
        if (coupon > 100 || principal > 50000 || yearsRemaining > 40) {
            throw new IllegalArgumentException("Invalid parameters, out of range");
        }
        return new Gilt(coupon, principal, yearsRemaining);
    }

    // Alternate constructor
    public static Gilt create(double coupon, int yearsRemaining) {
        // check input parameters
        return create(coupon, 100, yearsRemaining);
    }

    public double tick() {
        if (this.expired()) {
            throw new AlreadyExpiredGiltException(this);
        }
        this.yearsRemaining -= 1;
        if (this.yearsRemaining >= 1) {
            return this.coupon;
        } else {
            return this.coupon + this.principal;
        }
    }

    public boolean expired() {
        return this.yearsRemaining <= 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCoupon(double coupon) {
        this.coupon = coupon;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public void setYearsRemaining(int yearsRemaining) {
        this.yearsRemaining = yearsRemaining;
    }

    @Override
    public String toString() {
        return "Gilt {" +
                "id = " + id +
                " coupon = " + coupon +
                ", principal = " + principal +
                ", yearsRemaining = " + yearsRemaining +
                "}";
    }
}
