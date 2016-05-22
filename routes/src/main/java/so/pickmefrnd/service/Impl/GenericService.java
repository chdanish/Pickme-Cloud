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
package so.pickmefrnd.service.Impl;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;

import so.pickme.replica.domain.Entity;
import so.pickme.replica.domain.FriendRequest;
import so.pickme.replica.domain.User;
import so.pickme.replica.domain.UserQuery;
import so.pickme.repository.FriendrequestRepository;
import so.pickme.repository.UserRepository;
import so.pickme.utils.SecurityUtils;
import so.pickmefrnd.service.Service;

public abstract class GenericService<T> implements Service<T> {

    private static final int DEPTH_LIST = 0;
    private static final int DEPTH_ENTITY = 1;
    
    

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
    
    //Friendrequest services-----------------------------------------------------
    
    @Override
	public Iterable<UserQuery> findAllfriendrequestbyusername(Principal principal) {
		String username = SecurityUtils.getLoggedInUserName(principal);
		return userRepository.myfriendrequestMOD(username);
	}
    
    @Override
    public Iterable<UserQuery> addfriendrequestbyusername(String message,String  username,Principal principal){
    	String fromUsername = SecurityUtils.getLoggedInUserName(principal);
    	User fromUser = userRepository.findByUsername(fromUsername);
    	User toUser = userRepository.findByUsername(username);
    	FriendRequest frndreq = new FriendRequest(message, toUser, fromUser);
    	friendrequestRepository.save(frndreq);
		return userRepository.myfriendrequestMOD(username);
    	
    }
    
    @Override
    public Iterable<UserQuery> deletefriendrequestbyusername(String fromusername,Principal principal){
    	return findAllfriendrequestbyusername(principal);
  
    }
    
    //Friend services------------------------------------------------------------
    @Override
    public Iterable<User> findAllfriends(Principal principal){
    	String pusername= SecurityUtils.getLoggedInUserName(principal);
    	User loginUser = userRepository.findByUsername(pusername);
		return loginUser.getFriends();
    	
    }
    
    @Override
    public Iterable<User> addfriendbyusername(Principal principal,String  friendusername){
    	String pusername= SecurityUtils.getLoggedInUserName(principal);
    	User loginUser = userRepository.findByUsername(pusername);
    	User friendUser = userRepository.findByUsername(friendusername);
    	loginUser.addFriend(friendUser);
    	return findAllfriends(principal);
    	
    }
    @Override
    public Iterable<User> deletefriendbyusername(Principal principal,String  friendusername){
    	String pusername= SecurityUtils.getLoggedInUserName(principal);
    	User loginUser = userRepository.findByUsername(pusername);
    	User friendUser = userRepository.findByUsername(friendusername);
    	loginUser.deleteFriend(friendUser);
    	userRepository.save(loginUser);
		return findAllfriends(principal);
    	
    }
    
    
    //Find User\Friend services------------------------------------------------------------
    @Override
    public User findByUsername(String username){
    	
		return userRepository.findByUsername(username);
    	
    }
    @Override
    public Iterable<User> findByName(String name){
		return null;
    	
    }
    @Override
    public User findByEmail(String email){
    	return userRepository.findByEmail(email);
    	
    }
    
    

    public abstract GraphRepository<T> getRepository();
    
    @Autowired
    FriendrequestRepository friendrequestRepository;
    
    @Autowired
    UserRepository userRepository;
}
