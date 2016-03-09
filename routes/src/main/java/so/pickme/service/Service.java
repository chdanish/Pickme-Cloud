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

import java.util.List;

import so.pickme.replica.domain.Route;

public interface Service<T> {

    Iterable<T> findAll();
    
    List<Route> findAll(String ownedby);

    T find(Long id);

    void delete(Long id);

    T createOrUpdate(T object);

}
