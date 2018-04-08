package ptr.store.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptr.store.model.Category;
import ptr.store.model.OrderItem;

import java.util.List;

@Repository("orderItemDao")
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

    List<OrderItem> findByCategory(@Param("category") Category category);
}
