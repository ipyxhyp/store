package ptr.store.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ptr.store.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {


    Customer findCustomerByName(@Param("name") String name);

    Customer findCustomerByNameAndContactNumberAndEmail(@Param("name") String name, @Param("contactNumber") String contactNumber, @Param("email") String email);
}
