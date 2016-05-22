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
package so.pickmefrnd.service;

import java.security.Principal;

import so.pickme.replica.domain.User;
import so.pickme.replica.domain.UserQuery;

public interface Service<T> {

    Iterable<T> findAll();

    T find(Long id);

    void delete(Long id);

    T createOrUpdate(T object);
    
    
    //Friend request services
    
    Iterable<UserQuery> findAllfriendrequestbyusername(Principal principal);
    
    Iterable<UserQuery> addfriendrequestbyusername(String message,String  username,Principal principal);
    
    Iterable<UserQuery> deletefriendrequestbyusername(String fromusername,Principal principal);
    
    //Friend request services
    
    Iterable<User> findAllfriends(Principal principal);
    
    Iterable<User> addfriendbyusername(Principal principal,String  friendusername);
    
    Iterable<User> deletefriendbyusername(Principal principal,String  friendusername);
    
    //Find user\friend Services
    
    User findByUsername(String Username);
    
    Iterable<User> findByName(String name);
    
    User findByEmail(String email);
    
    

}
