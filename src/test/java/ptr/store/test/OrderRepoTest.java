package ptr.store.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ptr.store.config.InMemoryConfiguration;
import ptr.store.dao.CategoryRepository;
import ptr.store.dao.OrderItemRepository;
import ptr.store.dao.OrderRepository;
import ptr.store.model.Category;
import ptr.store.model.Order;
import ptr.store.model.OrderItem;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InMemoryConfiguration.class})
public class OrderRepoTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional()
    public void testFindByNameOrder(){
        // given
        String orderName = "test order";
        Order savedOrder = orderRepository.save(Order.builder().id(100L).name(orderName).amount(100.5).build());
        // when
        Order expectedOrder = orderRepository.findByName(orderName);
        // then
        Assert.assertEquals(expectedOrder, savedOrder);
    }

    @Test
    @Transactional()
    public void testFindOrderItemByAmount(){
        // given
        List<Order> ordersList = Arrays.asList(
                Order.builder().name("order 1").amount(100.5).build() ,
                Order.builder().name("order 2").amount(100.5).build());
        // when
        orderRepository.save(ordersList);
        List<Order> expectedOrders = orderRepository.findByAmount(100.5);
        // then
        Assert.assertEquals(expectedOrders, ordersList);
    }

    @Test
    @Transactional()
    public void testFindOrderByCreatedDate(){
        // given
        Date nowDate = new Date();
        List<Order> ordersList = Arrays.asList(
                Order.builder().name("first order").amount(100.5).createdDate(nowDate).build() ,
                Order.builder().name("second order").amount(200.5).createdDate(nowDate).build());
        orderRepository.save(ordersList);
        // when
        List<Order> expectedOrders = orderRepository.findByCreatedDate(nowDate);
        // then
        Assert.assertEquals(expectedOrders, ordersList);
    }

    @Test
    @Transactional()
    public void testFindOrderItemByOrder(){
        // given
        Category category = Category.builder().name("laptop category").build();
        List<OrderItem> orderItemList = Arrays.asList(
                OrderItem.builder().name("laptop test").price(11000.5)
                        .description("hp asteroid silver")
                        .category(category)
                        .build(),
                OrderItem.builder().name("desktop test").price(22000.5)
                        .description("HP 24-e013nc")
                        .category(category)
                        .build());
        category.setCategoryItems(orderItemList);
        // when
        categoryRepository.save(category);
        Category laptopCategory = categoryRepository.findByName("laptop category");
        // then
        Assert.assertNotNull(laptopCategory);
        Assert.assertNotNull(laptopCategory.getCategoryItems());
        Assert.assertEquals(laptopCategory, category);
    }
}
