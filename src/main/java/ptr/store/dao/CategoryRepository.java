package ptr.store.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptr.store.model.Category;

import java.util.List;

@Repository("categoryDao")
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByName(@Param("name") String name);

    List<Category> findCategoriesByName(@Param("name") String categoryName);
}
