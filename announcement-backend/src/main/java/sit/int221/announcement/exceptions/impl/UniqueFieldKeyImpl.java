package sit.int221.announcement.exceptions.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sit.int221.announcement.exceptions.validator.UniqueFieldKey;

import java.util.List;


public class UniqueFieldKeyImpl implements ConstraintValidator<UniqueFieldKey, Object> {

    @PersistenceContext
    private EntityManager manager;

    private Class<?> entityClass;
    private String field;

    @Override
    public void initialize(UniqueFieldKey annotation) {
        this.entityClass = annotation.model();
        this.field =  annotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Object> query = builder.createQuery();
        Root<?> root = query.from(entityClass);

        Predicate predicate = builder.equal(root.get(field), value);
        query.where(predicate);
        TypedQuery<Object> typedQuery = manager.createQuery(query);
        List<Object> sets = typedQuery.getResultList();

        return sets.isEmpty();
    }

}