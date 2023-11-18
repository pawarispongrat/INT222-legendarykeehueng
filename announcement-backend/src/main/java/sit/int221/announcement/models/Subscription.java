package sit.int221.announcement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.annotations.Check;
import sit.int221.announcement.models.ids.SubscriptionId;

import java.lang.annotation.Annotation;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table( name = "subscription" )
@Embeddable
@NoArgsConstructor
@IdClass(SubscriptionId.class)
public class Subscription {

    @Id
    private String subscriberEmail;

    @Id
    private Integer categoryId;

    @Column(insertable = false,updatable = false)
    private ZonedDateTime createdOn;

    @Column(insertable = false,updatable = false)
    private ZonedDateTime updatedOn;

    public Subscription(String email,Integer categoryId) {
        this.subscriberEmail = email;
        this.categoryId = categoryId;
    }


}
