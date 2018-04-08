package ptr.store.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Order entity
 * Has relation to the @see @Entity User/Category/Item
 *
 * **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "customer")
@ToString(exclude = "customer")
@Entity
@Table(name = "Orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Collection<OrderItem> orderItems;

    @ManyToOne
    private Customer customer;
    
}
