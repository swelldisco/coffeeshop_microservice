package com.java_coffee.coffee_service.coffeeOrder;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JoinColumn(name = "coffee_id", nullable = false, foreignKey = @ForeignKey(name = "order_coffee_fk"))
    private Coffee coffee;

    @ManyToOne()
    @JoinColumn(name =  "cart_id", nullable = false, foreignKey = @ForeignKey(name = "order_cart_fk"))
    @JsonBackReference
    private Cart cart;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public CoffeeOrder(Coffee coffee, Cart cart, int quantity) {
        this.coffee = coffee;
        this.cart = cart;
        this.quantity = quantity;
    }

    public CoffeeOrder(CoffeeOrder source) {
        this.orderId = source.orderId;
        this.coffee = source.coffee;
        this.cart = source.cart;
        this.quantity = source.quantity;
    }

    public long getOrderId() {
        return orderId;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (orderId ^ (orderId >>> 32));
        result = prime * result + ((coffee == null) ? 0 : coffee.hashCode());
        result = prime * result + ((cart == null) ? 0 : cart.hashCode());
        result = prime * result + quantity;
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
        if (cart == null) {
            if (other.cart != null)
                return false;
        } else if (!cart.equals(other.cart))
            return false;
        if (quantity != other.quantity)
            return false;
        return true;
    }

   
    
}
