package so.pickme.service;

import org.springframework.data.neo4j.repository.GraphRepository;

import so.pickme.replica.domain.Entity;


public abstract class GenericService<T> implements Service<T> {

    private static final int DEPTH_LIST = 0;
    private static final long DEPTH_ENTITY = 1;

    @Override
    public Iterable<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public T find(Long id) {
        return getRepository().findOne(id);
    }

    @Override
    public void delete(Long id) {
        getRepository().delete(id);
    }

    @Override
    public T createOrUpdate(T entity) {
        getRepository().save(entity);
        return find(((Entity) entity).getId());
    }

    public abstract GraphRepository<T> getRepository();
}
