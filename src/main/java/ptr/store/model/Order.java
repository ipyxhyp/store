package ptr.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Order entity
 * Has relation to the @see @Entity User/Category/Item
 *
 * **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

}
