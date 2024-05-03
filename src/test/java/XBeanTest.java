import model.XBean;
import org.junit.jupiter.api.Test;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import static org.junit.jupiter.api.Assertions.*;

class XBeanTest {

    @Test
    void testGetXValue() {
        XBean xBean = new XBean();
        xBean.setXValue(2.0);
        assertEquals(2.0, xBean.getXValue());
    }

    @Test
    void testSetXValue() {
        XBean xBean = new XBean();
        xBean.setXValue(3.0);
        assertEquals(3.0, xBean.getXValue());
    }

    @Test
    void testValidateXValue() {
        XBean xBean = new XBean();
        FacesContext facesContext = null;
        UIComponent uiComponent = null;

        // Test with null value
        Exception exception = assertThrows(ValidatorException.class, () -> {
            xBean.validateXValue(facesContext, uiComponent, null);
        });

        String expectedMessage = "Input X!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        // Test with non-null value
        assertDoesNotThrow(() -> {
            xBean.validateXValue(facesContext, uiComponent, 3.0);
        });
    }

    @Test
    void testEquals() {
        XBean xBean1 = new XBean();
        xBean1.setXValue(4.0);

        XBean xBean2 = new XBean();
        xBean2.setXValue(4.0);

        assertEquals(xBean1, xBean2);
    }

//    @Test
//    void testHashCode() {
//        XBean xBean = new XBean();
//        xBean.setXValue(5.0);
//        int expectedHashCode = Double.valueOf(5.0).hashCode();
//        assertEquals(expectedHashCode, xBean.hashCode());
//    }

    @Test
    void testToString() {
        XBean xBean = new XBean();
        xBean.setXValue(6.0);
        String expectedString = "XBean{value6.0}";
        assertEquals(expectedString, xBean.toString());
    }
}