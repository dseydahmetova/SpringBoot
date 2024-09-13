package gilts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GiltTest {
    Gilt g, g1, g2;

    @BeforeEach
    public void initialGilt() {
        g = Gilt.create(1.25, 100, 20);
        g1 = Gilt.create(1.6, 0);
        g2 = Gilt.create(0.75, 1);
    }

    @Test
    void testCreateGiltWithNegativeCoupon() {
        assertThrows(IllegalArgumentException.class, () -> Gilt.create(-1.25, 100.00, 20));
    }

    @Test
    void testCreateGiltWithNegativePrincipal() {
        assertThrows(IllegalArgumentException.class, () -> Gilt.create(1.25, -100.00, 20));
    }

    @Test
    void testCreateGiltWithNegativeYear() {
        assertThrows(IllegalArgumentException.class, () -> Gilt.create(1.25, 100.00, -20));
    }

    @Test
    void testGiltWithErroneousCoupon() {
        assertThrows(IllegalArgumentException.class, () -> Gilt.create(125, 100.00, 20));
    }

    @Test
    void testGiltWithErroneousYear() {
        assertThrows(IllegalArgumentException.class, () -> Gilt.create(1.25, 100.00, 100));
    }

    @Test
    void testGiltWithErroneousPrincipal() {
        assertThrows(IllegalArgumentException.class, () -> Gilt.create(1.25, 10000000000.00, 20));
        assertThrows(IllegalArgumentException.class, () -> Gilt.create(1.25, 0, 20));
    }

   @Test
    void testGetCouponPercent() {
        assertEquals(0.0125, g.getCouponPercent(), 0.01);
        assertEquals(0.016, g1.getCouponPercent(), 0.01);
        assertNotEquals(null, g.getCouponPercent());
    }

    @Test
    void testGetCoupon() {
        assertEquals(1.25, g.getCoupon(), 0.01);
        assertNotEquals(null, g.getCoupon());
    }

    @Test
    void getPrincipal() {
        assertEquals(100.00, g.getPrincipal(), 0.01);
        assertNotEquals(null, g.getPrincipal());
    }

    @Test
    void getYearsRemaining() {
        assertEquals(20.00, g.getYearsRemaining(), 0.01);
        assertNotEquals(Optional.ofNullable(null), g.getYearsRemaining());
    }

    @Test
    void testExpired() {
        assertFalse(g.expired());
    }

    @Test
    void testExpiredWith0YearRemaining() {
        assertTrue(g1.expired());
    }

    @Test
    void testTick() {
        assertEquals(1.25, g.tick(), 0.01);
        assertEquals(19, g.getYearsRemaining(), 0.01);
    }

    @Test
    void testTick1YearRemaining() {
        assertEquals(100.75, g2.tick(), 0.01);
    }

    @Test
    void testTick0YearRemaining() {
        assertThrows(AlreadyExpiredGiltException.class, () -> g1.tick());
    }
}
