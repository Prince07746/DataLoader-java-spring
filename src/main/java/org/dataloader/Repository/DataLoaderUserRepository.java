package org.dataloader.Repository;

import org.dataloader.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;

public interface DataLoaderUserRepository extends MongoRepository<User,String> {

}
