package ptr.store.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ptr.store.dao.CategoryRepository;
import ptr.store.dao.CustomerRepository;
import ptr.store.model.Category;
import ptr.store.model.Customer;
import ptr.store.model.Order;
import ptr.store.model.OrderItem;
import ptr.store.service.StoreService;

import java.util.*;

import static java.util.Optional.ofNullable;

@Service
@Qualifier("storeServiceImpl")
public class StoreServiceImpl implements StoreService {

    private final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

    private CategoryRepository categoryRepository;

    private CustomerRepository customerRepository;

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }


    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }



    @Override
    public Category createCategory(String categoryName) {
        Category categoryNew = categoryRepository.save(Category.builder().name(categoryName).build());
        log.trace("new category created: {} ", categoryNew);
        return categoryNew;
    }


    @Override
    public void renameCategoryName(String categoryName, String newCategoryName){
        Optional<Category> categoryFound = ofNullable(categoryRepository.findByName(categoryName));
        if (categoryFound.isPresent()) {
            Category category = categoryFound.get();
            category.setName(newCategoryName);
            categoryRepository.save(category);
            log.trace(" category name: {} updated  {}", newCategoryName, category);
        } else {
            throwNoSuchElementException(categoryName);
        }
    }

    @Override
    public void updateCategoryItems(List<OrderItem> orderItems, String categoryName) {
        Optional<Category> categoryFound = ofNullable(categoryRepository.findByName(categoryName));
        if (categoryFound.isPresent()) {
            categoryFound.get().setCategoryItems(orderItems);
            log.trace(" category : {} updated with category items {}", categoryFound.get(), categoryFound.get().getCategoryItems());
        } else {
            throwNoSuchElementException(categoryName);
        }
    }

    @Override
    public void deleteCategory(String categoryName) {
        Optional<Category> categoryFound = ofNullable(categoryRepository.findByName(categoryName));
        if (categoryFound.isPresent()) {
            categoryRepository.delete(categoryFound.get());
            log.trace(" category {} deleted ", categoryFound.get() );
        } else {
            throwNoSuchElementException(categoryName);
        }
    }

    @Override
    public List<Category> getCategoriesByName(String categoryName) {
        List<Category> categories = categoryRepository.findCategoriesByName(categoryName);
        log.trace(" categories by name {} found {} ", categoryName, categories);
        return categories;
    }

    @Override
    public List<Category> getCategoriesAll() {
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        log.trace(" found all categories {} ", categories);
        return categories;
    }

    @Override
    public Customer createCustomer(String customerName, String email, String contactNumber) {

        Customer customerNew = customerRepository.save(Customer.builder()
                .name(customerName)
                .email(email)
                .contactNumber(contactNumber)
                .build()
        );
        log.trace("new Customer created: {} ", customerNew);
        return customerNew;
    }

    @Override
    public Customer findCustomer(String name, String email, String contactNumber){
        Customer customerFound = customerRepository.findCustomerByNameAndContactNumberAndEmail(name, contactNumber, email );
        log.trace(" Customer found: {} ", customerFound);
        return customerFound;
    }

    @Override
    public void updateCustomer(String name, String email, String contactNumber) {
        // TODO impl
        log.trace(" Updating customer, {}, {}, {} ", name, email, contactNumber);
    }

    @Override
    public void deleteCustomer(String name, String email, String contactNumber) {
        // TODO impl
        log.trace(" Deleting customer, {}, {}, {} ", name, email, contactNumber);
    }

    @Override
    public void addOrderToCustomer(Order order) {
        // TODO implement
        log.trace(" Adding order  {} ", order);
    }

    @Override
    public void removeOrderFromCustomer(Order order, Customer customer) {
        // TODO implement
        log.trace(" Removing order {} from customer {} ", order, customer);
    }

    @Override
    public Collection<Order> getCustomerOrdersAll(Customer customer) {
        // TODO implement
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<Order> getCustomerOrdersByCreatedDate(Date createdDate, Customer customer) {
        // TODO implement
        return Collections.EMPTY_LIST;
    }

    private void throwNoSuchElementException(String categoryName) {
        log.trace(" category name: {} not found ", categoryName);
        throw new NoSuchElementException(String.format(" category name %s not found", categoryName));
    }
}
