package so.pickme.service;


import java.util.List;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import so.pickme.replica.domain.Route;
import so.pickme.repository.RouteRepository;
import so.pickme.service.Impl.Myrouteservice;

@Service("RouteService")
public class Myrouteserviceimpl extends GenericService<Route> implements Myrouteservice {

    @Autowired
    private RouteRepository routerepository;


    @Override
    public GraphRepository<Route> getRepository() {
        return routerepository;
    }

    @Override
	public List<Route> findAll(String ownedby) {
		// Non Generic Method 
		return routeRepository.findAll(ownedby);
	}
	
}



