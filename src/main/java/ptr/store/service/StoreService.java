package ptr.store.service;

import ptr.store.model.Category;
import ptr.store.model.Customer;
import ptr.store.model.Order;
import ptr.store.model.OrderItem;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface StoreService {

    Category createCategory(String name);

    void renameCategoryName(String categoryName, String newCategoryName);

    void updateCategoryItems(List<OrderItem> orderItems, String name);

    void deleteCategory(String name);

    List<Category> getCategoriesByName(String name);

    List<Category> getCategoriesAll();

    Customer createCustomer(String name, String email, String contactNumber);

    Customer findCustomer(String name, String email, String contactNumber);

    void updateCustomer(String name, String email, String contactNumber);

    void deleteCustomer(String name, String email, String contactNumber);

    void addOrderToCustomer(Order order);

    // TODO customer or customerName ?
    void removeOrderFromCustomer(Order order, Customer customer);

    // TODO customer or customerName ?
    Collection<Order> getCustomerOrdersAll(Customer customer);

    // TODO customer or customerName ?
    Collection<Order> getCustomerOrdersByCreatedDate(Date createdDate, Customer customer);


}
