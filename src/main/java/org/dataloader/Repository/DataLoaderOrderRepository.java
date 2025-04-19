package org.dataloader.Repository;

import org.dataloader.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DataLoaderOrderRepository extends MongoRepository<Order,String> {

}
