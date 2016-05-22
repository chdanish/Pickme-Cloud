/*
 * Copyright [2011-2016] "Neo Technology"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */
package so.pickme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;

import so.pickme.replica.domain.Entity;
import so.pickme.repository.RouteRepository;

public abstract class GenericService<T> implements Service<T> {

    private static final int DEPTH_LIST = 0;
    private static final int DEPTH_ENTITY = 1;
    
    @Autowired
    RouteRepository routeRepository;

    @Override
    public Iterable<T> findAll() {
        return getRepository().findAll(DEPTH_LIST);
    }
    
    

    @Override
    public T find(Long id) {
        return getRepository().findOne(id, DEPTH_ENTITY);
    }

    @Override
    public void delete(Long id) {
        getRepository().delete(id);
    }

    @Override
    public T createOrUpdate(T entity) {
        getRepository().save(entity, DEPTH_ENTITY);
        return find(((Entity) entity).getId());
    }

    public abstract GraphRepository<T> getRepository();
}
