package app.repository;


import app.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String>{

    @Query("{items.size:?0}")
    List<Order> findBySize(String size);

    @Query("{items.type:?0}")
    List<Order> findByType(String type);
}
