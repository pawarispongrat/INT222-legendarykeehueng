package sit.int221.announcement.models.ids;

import java.io.Serializable;
import java.util.Objects;

public class SubscriptionId implements Serializable {

    private String subscriberEmail;
    private Integer category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionId that = (SubscriptionId) o;
        return Objects.equals(subscriberEmail, that.subscriberEmail) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriberEmail, category);
    }
}
