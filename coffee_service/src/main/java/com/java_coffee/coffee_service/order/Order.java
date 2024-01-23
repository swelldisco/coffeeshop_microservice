package com.java_coffee.coffee_service.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.java_coffee.coffee_service.coffee.Coffee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;

    @Column(name = "user_id", nullable = false)
    //@ManyToOne(fetch = FetchType.LAZY)
    private long userId;

    // this needs to reference the coffee entity somehow
    // or I think I need some order helper classes
    @Column(name = "my_order")
    private List<Coffee> myOrder;

    @Column(name = "time_stamp")
    @JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")
    private LocalDateTime timeStamp;

    public Order() {
        this.myOrder = new ArrayList<>();
        this.timeStamp = LocalDateTime.now();
    }

    public Order(long orderId, long userId, List<Coffee> myOrder, LocalDateTime timeStamp) {
        this.orderId = orderId;
        this.userId = userId;
        this.myOrder = myOrder;
        this.timeStamp = timeStamp;
    }


    protected void addCoffeeToOrder(Coffee coffee) {
        myOrder.add(coffee);
    }

    protected void removeCoffeeFromOrder(Coffee coffee) {
        myOrder.remove(coffee);
    }

    protected void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    protected long getOrderId() {
        return orderId;
    }

    protected void setUserId(long userId) {
        this.userId = userId;
    }

    protected long getUserId() {
        return userId;
    }

    protected LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    protected String getMyOrder() {
        StringBuilder sb = new StringBuilder();
        sb.append("Your order: ");
        for (Coffee coffee : myOrder) {
            sb.append("\n" + coffee.toString());
        }
        return sb.toString();
    }

    protected List<Coffee> getOrderList(){
        return myOrder;
    }

    protected void setMyOrder(List<Coffee> myOrder) {
        this.myOrder = myOrder;
    }

    protected String getTotal() {
        double cost = 0;
        String total = "Total: $";
        for (Coffee coffee : myOrder) {
            cost += coffee.getPrice();
        }
        return total + String.format("%.2f", cost);
    }

    @Override
    public String toString() {
        return getMyOrder();
    }


}
