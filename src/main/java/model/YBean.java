package model;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;

public class YBean implements Serializable {
    private Double yValue = 1.0;

    public Double getYValue(){
        return yValue;
    }


    public void setYValue(Double yValue){
        this.yValue = yValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YBean yBean = (YBean) o;
        return Objects.equals(yValue, yBean.yValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(yValue);
    }

    @Override
    public String toString() {
        return "YBean{" +
                "yValue=" + yValue +
                '}';
    }
}
