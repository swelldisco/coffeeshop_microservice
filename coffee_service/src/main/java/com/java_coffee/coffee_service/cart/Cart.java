package com.java_coffee.coffee_service.cart;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.pojo.UserStub;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cartId")
    //@JsonManagedReference
    @JsonIgnore
    private List<CoffeeOrder> orders;


    public Cart(UserStub customer) {
        this.userId = customer.getUserId();
        this.orders = new ArrayList<CoffeeOrder>();
    }

    public Cart(Cart source) {
        this.cartId = source.cartId;
        this.userId = source.userId;
        this.orders = source.orders;
    }

    public long getCartId() {
        return cartId;
    }

    public long getUserId() {
        return userId;
    }

    public List<CoffeeOrder> getOrders() {
        return orders;
    }


    public void setOrders(List<CoffeeOrder> orders) {
        this.orders = orders;
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
        return true;
    }


}
