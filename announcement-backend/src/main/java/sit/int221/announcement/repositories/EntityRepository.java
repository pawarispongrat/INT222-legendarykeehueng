package sit.int221.announcement.repositories;


public interface EntityRepository<T> {

    void refresh(T t);
}
