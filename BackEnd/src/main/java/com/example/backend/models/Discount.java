
package com.example.backend.models;

import java.math.BigDecimal;
import java.util.Date;

public class Discount {
    private int id;
    private String name;
    private BigDecimal discountValue;
    private String startDate;
    private String endDate;

    public Discount() {
    }

    public Discount(String name, BigDecimal discountValue, String startDate, String endDate) {
        this.name = name;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Discount(int id, String name, BigDecimal discountValue, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discountValue=" + discountValue +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
