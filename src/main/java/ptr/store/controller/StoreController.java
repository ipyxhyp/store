package ptr.store.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ptr.store.dao.OrderRepository;
import ptr.store.model.Order;

/**
 * Main application REST controller for managing and redirect user requests
 * located at context 'store/management'  , default page is store/management/salary
 * @see @method salary()
 *
 * */
@RestController
@RequestMapping("/management")
public class StoreController {

    private final Logger log = LoggerFactory.getLogger(StoreController.class);


    private OrderRepository orderRepository;

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    

    @GetMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getLoans(@RequestParam(value="name", required = false) String name) {

        Order order = Order.builder().name(name).amount(100.5).build();
        log.info(" response : {} ", order);
        Order tempOrder = orderRepository.findOne(100L);
        log.info(" order temp : {} ", tempOrder);
        return order;
    }


}
