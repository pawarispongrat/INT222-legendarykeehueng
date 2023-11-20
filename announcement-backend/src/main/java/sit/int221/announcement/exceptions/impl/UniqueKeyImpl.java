package sit.int221.announcement.exceptions.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.exceptions.utils.NodeBuilder;
import sit.int221.announcement.exceptions.validator.UniqueKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class UniqueKeyImpl implements ConstraintValidator<UniqueKey, Object> {

    @Autowired
    private HttpServletRequest request;

    @PersistenceContext
    private EntityManager manager;

    private Class<?> entityClass;
    private String[] fields;
    private String primaryKey;

    private boolean isComposite;

    @Override
    public void initialize(UniqueKey annotation) {
        this.entityClass = annotation.model();
        this.fields =  annotation.fields();
        this.isComposite = annotation.composite();
        this.primaryKey = "id"; //KEY FROM PARAMETERS AND ID KEY
    }

    @Override
    public boolean isValid(Object target, ConstraintValidatorContext context) {
        Map map = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Object id = map.get(this.primaryKey);
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        Boolean[] booleans = new Boolean[fields.length];
        for (int i = 0 ; i < booleans.length ; i++) {
            String field = fields[i];
            booleans[i] = isUnique(builder,target,field,id);
            if (!booleans[i]) new NodeBuilder(context).buildPropertyNode(field);
        }
        Stream<Boolean> stream = Arrays.stream(booleans);
        return isComposite ? stream.anyMatch(Boolean::valueOf) : stream.allMatch(Boolean::valueOf);
    }

    private boolean isUnique(CriteriaBuilder builder,Object target,String field,Object id) {
        CriteriaQuery<Object> query = builder.createQuery();
        Root<?> root = query.from(entityClass);
        List<Predicate> predicates = new ArrayList<>();
        // SELECT u FROM User u WHERE id <> :id AND username = :username
        if (id != null) predicates.add(builder.notEqual(root.get(this.primaryKey), id));
        Path<?> path = root.get(field);
        Object value = getValue(target,field);
        if (value instanceof ArrayList<?> values) predicates.add(path.in(values));
        else predicates.add(builder.equal(path, value));
        query.where(predicates.toArray(new Predicate[0]));
        return manager.createQuery(query).getResultList().isEmpty();
    }

    public Object getValue(Object o,String field) {
        Class<?> clazz = o.getClass();
        try {
            Field propertyField = clazz.getDeclaredField(field);
            propertyField.setAccessible(true);
            return propertyField.get(o);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ItemNotFoundException(field);
        }
    }

}