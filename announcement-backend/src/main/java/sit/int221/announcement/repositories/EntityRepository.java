package sit.int221.announcement.repositories;


public interface EntityRepository<T> {

    <S extends T> void refresh(S t);
}
