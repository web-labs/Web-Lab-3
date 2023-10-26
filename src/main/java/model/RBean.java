package model;

import org.primefaces.event.SlideEndEvent;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;


public class RBean implements Serializable {

    private Double rValue = 2.25;

    public Double getRValue(){
        return rValue;
    }

    public void setRValue(Double rValue){
        this.rValue = rValue;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RBean rBean = (RBean) o;
        return Objects.equals(rValue, rBean.rValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rValue);
    }

    @Override
    public String toString() {
        return "RBean{" +
                "rValue=" + rValue +
                '}';
    }
}
