package ptr.store.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ptr.store.config.InMemoryConfiguration;
import ptr.store.dao.OrderRepository;
import ptr.store.model.Order;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InMemoryConfiguration.class})
public class OrderRepoTest {

    @Autowired
    private OrderRepository orderRepository;
    

    @Test
    @Transactional()
    public void testFindByNameOrder(){
        Order savedOrder = orderRepository.save(Order.builder().id(100L).name("test name").amount(100.5).build());
        Order expectedOrder = orderRepository.findByName("test name");
        Assert.assertEquals(expectedOrder, savedOrder);
    }

    @Test
    @Transactional()
    public void testFindByAmount(){
        List<Order> ordersList = Arrays.asList(
                Order.builder().name("test name").amount(100.5).build() ,
                Order.builder().name("another name").amount(100.5).build());
        orderRepository.save(ordersList);

        List<Order> expectedOrders = orderRepository.findByAmount(100.5);
        Assert.assertEquals(expectedOrders, ordersList);
    }

}
