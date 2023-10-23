package model;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;


public class XBean implements Serializable {

    private Double xValue = 0.0;

    public Double getXValue(){
        return xValue;
    }

    public void setXValue(Double value){
        this.xValue = value;
    }

    public void validateXValue(FacesContext facesContext, UIComponent uiComponent, Object o){
        if (o == null){
            FacesMessage message = new FacesMessage("Input X!");
            throw new ValidatorException(message);
        }
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (!(o instanceof XBean)) return false;

        XBean that = (XBean) o;
        return Double.compare(getXValue(), that.getXValue()) == 0;
    }

    @Override
    public int hashCode(){
        return Objects.hash(getXValue());
    }

    @Override
    public String toString(){
        return "XBean{" +
                "value" + xValue +
                "}";
    }
}
