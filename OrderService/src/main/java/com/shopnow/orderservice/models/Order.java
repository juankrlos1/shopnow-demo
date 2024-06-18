package com.shopnow.orderservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private List<OrderItem> items;
    private Double totalAmount;
    private Date orderDate;
    private OrderStatus status;
}

@Data
@NoArgsConstructor
class OrderItem {
    private String productId;
    private Integer quantity;
    private Double price;
}

enum OrderStatus {
    PENDING, SHIPPED, DELIVERED, CANCELED
}
