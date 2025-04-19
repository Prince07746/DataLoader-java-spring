package org.dataloader.Service;

import org.dataloader.Repository.DataLoaderProductRepository;
import org.dataloader.model.Product;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final DataLoaderProductRepository productRepo;
    private final MongoTemplate mongoTemplate;

    public ProductService(DataLoaderProductRepository productRepo, MongoTemplate mongoTemplate) {
        this.productRepo = productRepo;
        this.mongoTemplate = mongoTemplate;
    }

    // CREATE
    public void addProduct(Product product) {
        if (product == null || product.getName() == null || product.getPrice() == null) {
            throw new IllegalArgumentException("Product details are incomplete.");
        }
        productRepo.save(product);
    }

    // READ
    public List<Product> getProductList() {
        return productRepo.findAll();
    }

    public Optional<Product> getProduct(String id) {
        return productRepo.findById(id);
    }

    // UPDATE

    private Update getProductUpdate(Product product){
        return new Update()
                .set("name", product.getName())
                .set("price", product.getPrice());
    }

    public void updateProduct(Product product) { // this work has a helper logic to update Product
        if (product.getId() != null) {
            Query query = new Query(Criteria.where("_id").is(product.getId()));
            mongoTemplate.updateFirst(query, getProductUpdate(product), Product.class);
        }
    }

    public void updateProducts(List<Product> productList) {
        if (productList == null || productList.isEmpty()) return;
        for (Product product : productList) {
            if (product.getId() != null) {
                Query query = new Query(Criteria.where("_id").is(product.getId()));
                mongoTemplate.updateFirst(query, getProductUpdate(product), Product.class);
            }
        }
    }


    // DELETE
    public void deleteProduct(String id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
        }
    }

    public void deleteProducts(List<String> productIds) {
        if (productIds == null || productIds.isEmpty()) return;
        productRepo.deleteAllById(productIds);
    }

    public void deleteAllProduct(){
        productRepo.deleteAll();
    }
}
