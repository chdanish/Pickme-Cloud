package so.pickme.service;

import org.springframework.data.neo4j.template.Neo4jOperations;

import so.pickme.common.exception.XhrcException;
import so.pickme.replica.domain.Route;
import so.pickme.replica.domain.User;
import so.pickme.repository.RouteRepository;
import so.pickme.response.SaverouteDTO;


public interface RouteService {
	
	Neo4jOperations template();
	
	RouteRepository routeRepository();
	
	
	Route getRouteNode(Long userId) throws XhrcException;

	/**
	 * 
	 * 
	 * @param userNode 
	 */
	Route updateRouteNode(Long userId) throws XhrcException;

	/**
	 * 
	 * 
	 * 
	 */
	Route createRouterNode(SaverouteDTO sdto, User user) throws XhrcException;

	

}
