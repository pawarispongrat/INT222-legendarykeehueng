package sit.int221.announcement.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import sit.int221.announcement.repositories.EntityRepository;

import java.io.Serializable;

public class EntityRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements EntityRepository<T,ID> {

    private EntityManager manager;

    public EntityRepositoryImpl(JpaEntityInformation information, EntityManager manager) {
        super(information,manager);
        this.manager = manager;
    }


    @Override
    public void refresh(T t) {
        manager.refresh(t);
    }
}
