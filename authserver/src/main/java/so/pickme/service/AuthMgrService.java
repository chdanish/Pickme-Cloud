package so.pickme.service;

import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.authentication.AuthenticationManager;

import so.pickme.repository.UserRepository;

public interface AuthMgrService extends AuthenticationManager {
	
	Neo4jOperations template();

	UserRepository userRepository();

}
