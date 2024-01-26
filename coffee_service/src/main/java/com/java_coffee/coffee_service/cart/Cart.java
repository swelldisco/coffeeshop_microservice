package com.java_coffee.coffee_service.cart;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "user_id", unique = true)
    private long userId;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
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

    public long getuserId() {
        return userId;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (cartId ^ (cartId >>> 32));
        result = prime * result + (int) (userId ^ (userId >>> 32));
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
        Cart other = (Cart) obj;
        if (cartId != other.cartId)
            return false;
        if (userId != other.userId)
            return false;
        if (timeStamp == null) {
            if (other.timeStamp != null)
                return false;
        } else if (!timeStamp.equals(other.timeStamp))
            return false;
        return true;
    }

    

}
