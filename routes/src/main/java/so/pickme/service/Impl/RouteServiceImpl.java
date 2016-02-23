package so.pickme.service.Impl;

import java.security.Principal;
import java.util.Calendar;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import so.pickme.common.exception.XhrcException;
import so.pickme.replica.domain.Route;
import so.pickme.replica.domain.User;
import so.pickme.repository.RouteRepository;
import so.pickme.response.SaverouteDTO;
import so.pickme.service.RouteService;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {
	private static Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);
	
	@Resource
	private Neo4jOperations template;
	@Autowired
	private RouteRepository routeRepository;
	
	Principal user;
	

	public void setTemplate(Neo4jOperations template) {
		this.template = template;
	}
	
	@Override
	public Neo4jOperations template() {
		// TODO Auto-generated method stub
		return template;
	}
	
	public void setUserRepository(RouteRepository routeRepository) {
		this.routeRepository = routeRepository;
	}

	@Override
	public RouteRepository routeRepository() {
		// TODO Auto-generated method stub
		return routeRepository;
	}

	@Override
	public Route getRouteNode(Long userId) throws XhrcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route updateRouteNode(Long userId) throws XhrcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route createRouterNode(SaverouteDTO sdto,User user) throws XhrcException {
		try {
			Route route = new Route();
			
			route.setCreatedOn(Calendar.getInstance().getTime());
			
			route.setStartpointLAT(sdto.getStp_LAT());
			route.setStartpointLONG(sdto.getStp_LAT());
			
			route.setDestpointLAT(sdto.getDstp_LAT());
			route.setDestpointLONG(sdto.getDstp_LNG());
			
			/*User activeUser = (User) ((Authentication) principal).getPrincipal();*/
			route.setUser(user);
			
			route.setShareType("Public");
			
			return routeRepository().save(route);
		} catch (Exception e) {
			logger.error("saveUserNode userNode=" + sdto, e);
			throw new XhrcException("20021", "saveUserNode userNode=" + sdto, e);
		}
	}

}
