package sit.int221.announcement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sit.int221.announcement.models.Category;
import sit.int221.announcement.models.Subscription;
import sit.int221.announcement.models.ids.SubscriptionId;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
    Optional<Subscription> findBySubscriberEmail(String email);

    @Query("SELECT s.subscriberEmail FROM Subscription s WHERE s.categoryId = :categoryId")
    Optional<List<String>> findEmails(@Param("categoryId") Integer categoryId);
}
