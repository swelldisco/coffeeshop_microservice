package com.java_coffee.coffee_service.coffeeOrder;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java_coffee.coffee_service.cart.Cart;
import com.java_coffee.coffee_service.coffee.Coffee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coffee_order")
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder {
    
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @ManyToOne()
    @JoinColumn(name = "coffee_id", nullable = false, foreignKey = @ForeignKey(name = "coffee_id_FK"))
    @NotNull(message = "All orders must contain an item")
    private Coffee coffee;

    @Column(name = "cart_id")
    private long cartId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "quantity", nullable = false)
    @Positive
    private int quantity;

    @Column(name = "timestamp")
    @CreationTimestamp
    @JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")
    private LocalDateTime timeStamp;

    public CoffeeOrder(Coffee coffee, Cart cart, int quantity) {
        this.coffee = coffee;
        this.cartId = cart.getCartId();
        this.userId = cart.getuserId();
        this.quantity = quantity;
        this.timeStamp = LocalDateTime.now();
    }

    public CoffeeOrder(CoffeeOrder source) {
        this.orderId = source.orderId;
        this.coffee = source.coffee;
        this.cartId = source.cartId;
        this.userId = source.userId;
        this.quantity = source.quantity;
        this.timeStamp = source.timeStamp;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getCartId() {
        return cartId;
    }

    public long getUserId() {
        return userId;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp() {
        this.timeStamp = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (orderId ^ (orderId >>> 32));
        result = prime * result + ((coffee == null) ? 0 : coffee.hashCode());
        result = prime * result + (int) (cartId ^ (cartId >>> 32));
        result = prime * result + (int) (userId ^ (userId >>> 32));
        result = prime * result + quantity;
        result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
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
        CoffeeOrder other = (CoffeeOrder) obj;
        if (orderId != other.orderId)
            return false;
        if (coffee == null) {
            if (other.coffee != null)
                return false;
        } else if (!coffee.equals(other.coffee))
            return false;
        if (cartId != other.cartId)
            return false;
        if (userId != other.userId)
            return false;
        if (quantity != other.quantity)
            return false;
        if (timeStamp == null) {
            if (other.timeStamp != null)
                return false;
        } else if (!timeStamp.equals(other.timeStamp))
            return false;
        return true;
    }

    

    
    
}
