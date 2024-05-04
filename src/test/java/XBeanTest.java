import model.XBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class XBeanTest {

    private XBean xBean;

    @BeforeEach
    void setUp() {
        xBean = new XBean();
    }

    @Test
    void testGetXValue() {
        assertEquals(Double.valueOf(0.0), xBean.getXValue(), "Default value should be 0.0");
    }

    @Test
    void testSetXValue() {
        Double newValue = 1.0;
        xBean.setXValue(newValue);
        assertEquals(newValue, xBean.getXValue(), "Value should be updated to 1.0");
    }

    @Test
    void testEquals() {
        XBean anotherXBean = new XBean();
        assertTrue(xBean.equals(anotherXBean), "Two XBeans with the same value should be equal");

        anotherXBean.setXValue(1.0);
        assertFalse(xBean.equals(anotherXBean), "Should not be equal if values are different");
    }

    @Test
    void testHashCode() {
        XBean anotherXBean = new XBean();
        assertEquals(xBean.hashCode(), anotherXBean.hashCode(), "Hash codes should be the same for identical values");
    }

    @Test
    void testToString() {
        String expectedString = "XBean{value0.0}";
        assertEquals(expectedString, xBean.toString(), "toString should return correct representation");
    }
}