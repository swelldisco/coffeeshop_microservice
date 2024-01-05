package com.java_coffee.coffee_service.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.java_coffee.coffee_service.coffee.CoffeeDto;

public class OrderDto {
    private List<CoffeeDto> myOrder;
    private long orderId;
    private long userId;
    private LocalDateTime timeStamp;

    public OrderDto() {
        this.myOrder = new ArrayList<>();
        this.timeStamp = LocalDateTime.now();
    }

    public OrderDto(List<CoffeeDto> myOrder, long orderId, long userId, LocalDateTime timeStamp) {
        this.myOrder = myOrder;
        this.orderId = orderId;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }


    protected void addCoffeeToOrder(CoffeeDto coffeeDto) {
        myOrder.add(coffeeDto);
    }

    protected void removeCoffeeFromOrder(CoffeeDto coffeeDto) {
        myOrder.remove(coffeeDto);
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
        for (CoffeeDto coffeeDto : myOrder) {
            sb.append("\n" + coffeeDto.toString());
        }
        return sb.toString();
    }

    protected List<CoffeeDto> returnOrderList(){
        return myOrder;
    }

    protected void setMyOrder(List<CoffeeDto> myOrder) {
        this.myOrder = myOrder;
    }

    protected String getTotal() {
        double cost = 0;
        String total = "Total: $";
        for (CoffeeDto coffeeDto : myOrder) {
            cost += coffeeDto.getPrice();
        }
        return total + String.format("%.2f", cost);
    }

    @Override
    public String toString() {
        return getMyOrder();
    }

}
