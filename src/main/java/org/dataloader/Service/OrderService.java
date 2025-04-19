package org.dataloader.Service;

import org.dataloader.Repository.DataLoaderOrderRepository;
import org.dataloader.model.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final DataLoaderOrderRepository orderRepo;
    private final MongoTemplate mongoTemplate;

    public OrderService(DataLoaderOrderRepository orderRepo, MongoTemplate mongoTemplate) {
        this.orderRepo = orderRepo;
        this.mongoTemplate = mongoTemplate;
    }

    // CREATE
    public void addOrder(Order order) {
        if (order == null || order.getCustomerName() == null || order.getTotalAmount() == null) {
            throw new IllegalArgumentException("Order details are incomplete.");
        }
        orderRepo.save(order);
    }

    // READ
    public List<Order> getOrderList() {
        return orderRepo.findAll();
    }

    public Optional<Order> getOrder(String id) {
        return orderRepo.findById(id);
    }


    // UPDATE
    private Update getOrderUpdate(Order order){ // this work has a helper logic to update Order
        return new Update()
                .set("customerName", order.getCustomerName())
                .set("totalAmount", order.getTotalAmount())
                .set("orderDate", order.getOrderDate());
    }

    public void updateOrder(Order order) {
        if (order.getId() != null) {
            Query query = new Query(Criteria.where("_id").is(order.getId()));
            mongoTemplate.updateFirst(query, getOrderUpdate(order), Order.class);
        }
    }

    public void updateOrders(List<Order> orderList) {
        if (orderList == null || orderList.isEmpty()) return;
        for (Order order : orderList) {
            if (order.getId() != null) {
                Query query = new Query(Criteria.where("_id").is(order.getId()));
                mongoTemplate.updateFirst(query, getOrderUpdate(order), Order.class);
            }
        }
    }


    // DELETE
    public void deleteOrder(String id) {
        if (orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
        }
    }

    public void deleteOrders(List<String> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) return;
        orderRepo.deleteAllById(orderIds);
    }

    public void deleteAllOrders(){
        orderRepo.deleteAll();
    }

}
