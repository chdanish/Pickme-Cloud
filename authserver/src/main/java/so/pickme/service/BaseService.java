package so.pickme.service;


import so.pickme.common.exception.XhrcException;
import so.pickme.replica.domain.User;
import so.pickme.response.SignupDTO;

public interface BaseService {
	/*Neo4jOperations template();

	UserRepository userRepository();*/
	
	/**
	 * 
	 * 
	 * @param username
	 * @return
	 */
	//User findByUsername(String username);

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
