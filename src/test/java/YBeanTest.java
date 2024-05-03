import model.YBean;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class YBeanTest {

    @Test
    void testGetYValue() {
        YBean yBean = new YBean();
        yBean.setYValue(2.0);
        assertEquals(2.0, yBean.getYValue());
    }

    @Test
    void testSetYValue() {
        YBean yBean = new YBean();
        yBean.setYValue(3.0);
        assertEquals(3.0, yBean.getYValue());
    }

    @Test
    void testEquals() {
        YBean yBean1 = new YBean();
        yBean1.setYValue(4.0);

        YBean yBean2 = new YBean();
        yBean2.setYValue(4.0);

        assertEquals(yBean1, yBean2);
    }


    @Test
    void testToString() {
        YBean yBean = new YBean();
        yBean.setYValue(6.0);
        String expectedString = "YBean{yValue=6.0}";
        assertEquals(expectedString, yBean.toString());
    }
}