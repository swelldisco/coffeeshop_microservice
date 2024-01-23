package com.java_coffee.coffee_service.orderHelpers;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.order.Order;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// https://www.baeldung.com/spring-angular-ecommerce
// this is supposed to hold the composite primary keys
@Embeddable
public class OrderKeys implements Serializable{
    
    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "coffee_id")
    private Coffee coffee;

    public OrderKeys() {}

    public OrderKeys(Order order, Coffee coffee) {
        this.order = order;
        this.coffee = coffee;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((order == null) ? 0 : order.hashCode());
        result = prime * result + ((coffee == null) ? 0 : coffee.hashCode());
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
        OrderKeys other = (OrderKeys) obj;
        if (order == null) {
            if (other.order != null)
                return false;
        } else if (!order.equals(other.order))
            return false;
        if (coffee == null) {
            if (other.coffee != null)
                return false;
        } else if (!coffee.equals(other.coffee))
            return false;
        return true;
    }

    

}
