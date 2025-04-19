package org.dataloader.Repository;

import org.dataloader.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataLoaderProductRepository extends MongoRepository<Product,String> {

}
