package com.java_coffee.coffee_service.pojo;

import java.util.List;

import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrderDto;

public class OrderReceipt {
    
    private long userId;
    private String userName;
    private String emailAddress;
    private List<CoffeeOrderDto> order;
    private double total;

    public OrderReceipt(UserStub userStub, List<CoffeeOrderDto> order, double total) {
        this.userId = userStub.getUserId();
        this.userName = userStub.getUserName();
        this.emailAddress = userStub.getEmailAddress();
        this.order = order;
        this.total = total;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<CoffeeOrderDto> getOrder() {
        return order;
    }

    public double getTotal() {
        return total;
    }

    public void setOrder(List<CoffeeOrderDto> order) {
        this.order = order;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (userId ^ (userId >>> 32));
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result = prime * result + ((order == null) ? 0 : order.hashCode());
        long temp;
        temp = Double.doubleToLongBits(total);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderReceipt other = (OrderReceipt) obj;
        if (userId != other.userId)
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        if (emailAddress == null) {
            if (other.emailAddress != null)
                return false;
        } else if (!emailAddress.equals(other.emailAddress))
            return false;
        if (order == null) {
            if (other.order != null)
                return false;
        } else if (!order.equals(other.order))
            return false;
        if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
            return false;
        return true;
    }

}
