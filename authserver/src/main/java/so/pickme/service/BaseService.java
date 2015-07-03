package so.pickme.service;


import org.springframework.data.neo4j.template.Neo4jOperations;

import so.pickme.common.exception.XhrcException;
import so.pickme.domain.User;
import so.pickme.repository.UserRepository;
import so.pickme.response.SignupDTO;

public interface BaseService {
	Neo4jOperations template();

	UserRepository userRepository();

	/**
	 * 
	 * 
	 * @param userId
	 * @return
	 */
	User getUserNode(Long userId) throws XhrcException;

	/**
	 * 
	 * 
	 * @param userNode 
	 */
	User saveUserNode(User userNode) throws XhrcException;

	/**
	 * 
	 * 
	 * @param userId
	 */
	User registerUserNode(SignupDTO dto) throws XhrcException;
}
