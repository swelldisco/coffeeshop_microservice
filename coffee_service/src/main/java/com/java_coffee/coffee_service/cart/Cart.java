package com.java_coffee.coffee_service.cart;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.userStub.UserStub;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    // there's only a user stub to share info between services, so......
    // @OneToOne(fetch = FetchType.LAZY)
    // private UserStub customer;
    @Column(name = "user_id")
    private long userId;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CoffeeOrder> orders;

    @Column(name = "timestamp")
    @CreationTimestamp
    @JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")
    private LocalDateTime timeStamp;

    public Cart(UserStub customer) {
        this.userId = customer.getUserId();
        this.orders = new ArrayList<CoffeeOrder>();
        timeStamp = LocalDateTime.now();
    }

    public Cart(Cart source) {
        this.cartId = source.cartId;
        this.userId = source.userId;
        this.orders = source.orders;
        this.timeStamp = source.timeStamp;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getuserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<CoffeeOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<CoffeeOrder> orders) {
        this.orders = orders;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void emptyCart() {
        orders.clear();
    }

    public void addOrderToCart(CoffeeOrder coffeeOrder) {
        orders.add(coffeeOrder);
    }

    public void removeOrderFromCart(CoffeeOrder coffeeOrder) {
        orders.remove(coffeeOrder);
    }

}
