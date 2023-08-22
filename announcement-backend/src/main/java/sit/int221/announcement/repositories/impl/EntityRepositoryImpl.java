package sit.int221.announcement.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import sit.int221.announcement.repositories.EntityRepository;

public class EntityRepositoryImpl<T> implements EntityRepository<T> {

    @PersistenceContext
    private EntityManager manager;


    @Override
    @Transactional
    public void refresh(T o) {
        manager.refresh(o);
    }
}
