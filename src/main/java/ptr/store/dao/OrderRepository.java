package ptr.store.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptr.store.model.Order;

import java.util.List;

@Repository("orderDao")
public interface OrderRepository extends CrudRepository<Order, Long> {

    public Order findByName(@Param("name") String name);

    public List<Order> findByAmount(@Param("amount") Double amount);
    
}
