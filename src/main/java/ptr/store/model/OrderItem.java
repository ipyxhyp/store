package ptr.store.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"category", "order"})
@EqualsAndHashCode(exclude = {"category", "order"})
@Entity
@Table(name = "OrderItems")
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;


    @Column
    private Double price;


    @Column
    private String description;

    @ManyToOne
    private Order order;
    
    @ManyToOne
    private Category category;
}
