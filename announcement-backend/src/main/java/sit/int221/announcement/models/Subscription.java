package sit.int221.announcement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.models.ids.SubscriptionId;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table( name = "subscription" )
@IdClass(SubscriptionId.class)
public class Subscription {

    @Id
    private String subscriberEmail;

    @Id
    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;

    @Column(insertable = false,updatable = false)
    private ZonedDateTime createdOn;

    @Column(insertable = false,updatable = false)
    private ZonedDateTime updatedOn;

}
