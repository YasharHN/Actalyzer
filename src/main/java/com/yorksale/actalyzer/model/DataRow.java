package com.yorksale.actalyzer.model;

import java.io.Serializable;

/**
 * Created by admin on 2015-08-08.
 */
public class DataRow implements Serializable {
    private String label;
    private Number label2;
    private Number value;
    private Integer pos;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Number getLabel2() {
        return label2;
    }

    public void setLabel2(Number label2) {
        this.label2 = label2;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }
}
