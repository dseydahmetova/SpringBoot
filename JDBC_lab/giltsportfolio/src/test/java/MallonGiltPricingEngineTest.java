import gilts.Gilt;
import gilts.GiltPricingEngine;
import gilts.MallonGiltPricingEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MallonGiltPricingEngineTest {
    @Mock
    static Gilt sharedGilt;

    @BeforeEach
    void setup() {
        sharedGilt = mock(Gilt.class);
    }

    @Test
    void twoYearGiltTest() {
        GiltPricingEngine pricer = new MallonGiltPricingEngine(4.46, 4.50, 4.11, 4.23);
        when(sharedGilt.getYearsRemaining()).thenReturn(2);
        when(sharedGilt.getCoupon()).thenReturn(42.50);
        when(sharedGilt.getCouponPercent()).thenReturn(4.25);
        when(sharedGilt.getPrincipal()).thenReturn(1000.0);
        assertEquals(995.98, pricer.getPrice(sharedGilt), 0.01);
    }

    @Test
    void twoYearGiltTest1() {
        GiltPricingEngine pricer = new MallonGiltPricingEngine(2.35, 3.02, 3.56, 4.06);
        when(sharedGilt.getYearsRemaining()).thenReturn(2);
        when(sharedGilt.getCoupon()).thenReturn(3.25);
        when(sharedGilt.getCouponPercent()).thenReturn(0.0325);
        when(sharedGilt.getPrincipal()).thenReturn(100.0);
        assertEquals(101.76, pricer.getPrice(sharedGilt), 0.01);
    }

    @Test
    void fiveYearGiltTest1() {
        GiltPricingEngine pricer = new MallonGiltPricingEngine(2.35, 3.02, 3.56, 4.06);
        when(sharedGilt.getYearsRemaining()).thenReturn(5);
        when(sharedGilt.getCoupon()).thenReturn(10.25);
        when(sharedGilt.getCouponPercent()).thenReturn(0.1025);
        when(sharedGilt.getPrincipal()).thenReturn(1000.0);
        assertEquals(907.25, pricer.getPrice(sharedGilt), 0.01);
    }

    @Test
    void tenYearGiltTest1() {
        GiltPricingEngine pricer = new MallonGiltPricingEngine(2.35, 3.02, 3.56, 4.06);
        when(sharedGilt.getYearsRemaining()).thenReturn(10);
        when(sharedGilt.getCoupon()).thenReturn(22.0);
        when(sharedGilt.getCouponPercent()).thenReturn(0.22);
        when(sharedGilt.getPrincipal()).thenReturn(200.0);
        assertEquals(326.32, pricer.getPrice(sharedGilt), 0.01);
    }

    @Test
    void twentyYearGiltTest1() {
        GiltPricingEngine pricer = new MallonGiltPricingEngine(2.35, 3.02, 3.56, 4.06);
        when(sharedGilt.getYearsRemaining()).thenReturn(20);
        when(sharedGilt.getCoupon()).thenReturn(0.0);
        when(sharedGilt.getCouponPercent()).thenReturn(0.0);
        when(sharedGilt.getPrincipal()).thenReturn(1000.0);
        assertEquals(422.48, pricer.getPrice(sharedGilt), 0.01);
    }

    @Test
    void oneYearGiltTest1() {
        GiltPricingEngine pricer = new MallonGiltPricingEngine(2.35, 3.02, 3.56, 4.06);
        when(sharedGilt.getYearsRemaining()).thenReturn(1);
        when(sharedGilt.getCoupon()).thenReturn(1.0);
        when(sharedGilt.getCouponPercent()).thenReturn(0.01);
        when(sharedGilt.getPrincipal()).thenReturn(100.0);
        assertEquals(98.67, pricer.getPrice(sharedGilt), 0.01);
    }

    @Test
    void fourYearGiltTest1() {
        GiltPricingEngine pricer = new MallonGiltPricingEngine(2.35, 3.02, 3.56, 4.06);
        when(sharedGilt.getYearsRemaining()).thenReturn(4);
        when(sharedGilt.getCoupon()).thenReturn(10.0);
        when(sharedGilt.getCouponPercent()).thenReturn(0.1);
        when(sharedGilt.getPrincipal()).thenReturn(1000.0);
        assertEquals(923.80, pricer.getPrice(sharedGilt), 0.01);
    }

    @Test
    void eightYearGiltTest1() {
        GiltPricingEngine pricer = new MallonGiltPricingEngine(2.35, 3.02, 3.56, 4.06);
        when(sharedGilt.getYearsRemaining()).thenReturn(8);
        when(sharedGilt.getCoupon()).thenReturn(2250.0);
        when(sharedGilt.getCouponPercent()).thenReturn(22.50);
        when(sharedGilt.getPrincipal()).thenReturn(100000.0);
        assertEquals(90826.33, pricer.getPrice(sharedGilt), 0.01);
    }

    @Test
    void fifteenYearGiltTest1() {
        GiltPricingEngine pricer = new MallonGiltPricingEngine(2.35, 3.02, 3.56, 4.06);
        when(sharedGilt.getYearsRemaining()).thenReturn(15);
        when(sharedGilt.getCoupon()).thenReturn(0.5);
        when(sharedGilt.getCouponPercent()).thenReturn(0.005);
        when(sharedGilt.getPrincipal()).thenReturn(50.0);
        assertEquals(32.41, pricer.getPrice(sharedGilt), 0.01);
    }
}
